package com.example.composeapp.data.network

import android.util.Log
import com.example.composeapp.data.mapper.NewsFeedMapper
import com.example.composeapp.data.model.AttachmentDto
import com.example.composeapp.data.model.CommentDto
import com.example.composeapp.data.model.CommentsContentDto
import com.example.composeapp.data.model.CommentsDto
import com.example.composeapp.data.model.CommentsResponseDto
import com.example.composeapp.data.model.GroupDto
import com.example.composeapp.data.model.LikesDto
import com.example.composeapp.data.model.NewsFeedContentDto
import com.example.composeapp.data.model.NewsFeedResponseDto
import com.example.composeapp.data.model.PhotoDto
import com.example.composeapp.data.model.PhotoUrlDto
import com.example.composeapp.data.model.PostDto
import com.example.composeapp.data.model.ProfileDto
import com.example.composeapp.data.model.RepostsDto
import com.example.composeapp.data.model.ViewsDto
import com.example.composeapp.domain.model.FeedPost
import com.example.composeapp.domain.model.PostComment
import com.example.composeapp.domain.model.StatisticItem
import com.example.composeapp.domain.model.StatisticType
import com.example.composeapp.domain.repoository.NewsFeedRepository
import com.example.composeapp.mergeWith
import com.example.composeapp.presentation.main.AuthState
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: NewsFeedMapper,
    private val storage: VKPreferencesKeyValueStorage,
) : NewsFeedRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val token
        get() = VKAccessToken.restore(storage)

    // Authorization
    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = 1)
    private val authStateFlow = flow {
        checkAuthStateEvents.emit(Unit)
        checkAuthStateEvents.collect {
            val currentToken = token
            val loggedIn = currentToken != null && currentToken.isValid
            val authState = if (loggedIn) AuthState.Authorized else AuthState.NotAuthorized
            emit(authState)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

    // Cached Recommendations
    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    // Recommendations
    private var nextFrom: String? = null

    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()

    private val loadedListFlow = flow {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom
            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }
            var response = if (startFrom == null) {
                apiService.loadRecommendations(getAccessToken())
            } else {
                apiService.loadRecommendations(getAccessToken(), startFrom)
            }
            // Метод апи перестал поддерживаться, поэтому временно заменен на моки
            if (response.newsFeedContent.posts.isEmpty()) {
                response = mockNewsFeedResponse()
            }
            Log.d("MainScreen", "loadedListFlow: response = $response")
            nextFrom = response.newsFeedContent.nextFrom
            val posts = mapper.mapResponseToPosts(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }

    fun mockNewsFeedResponse(): NewsFeedResponseDto {
        val groups = listOf(
            GroupDto(
                id = 1L,
                name = "VK Developers",
                imageUrl = "https://vk.com/images/community_200.png"
            ),
            GroupDto(
                id = 2L,
                name = "Android News",
                imageUrl = "https://vk.com/images/android_200.png"
            )
        )

        val posts = (1..15).map { index ->
            PostDto(
                id = index.toLong(),
                communityId = if (index % 2 == 0) 1L else 2L,
                text = "Тестовый пост №$index\nЭто моковые данные для ленты VK.",
                date = System.currentTimeMillis() / 1000,
                likes = LikesDto(
                    count = (10..500).random(),
                    userLikes = (10..500).random()
                ),
                comments = CommentsDto(count = (0..100).random()),
                views = ViewsDto(count = (100..10_000).random()),
                reposts = RepostsDto(count = (0..50).random()),
                attachments = listOf(
                    AttachmentDto(
                        photo = PhotoDto(
                            photoUrls = listOf(
                                PhotoUrlDto(
                                    url = "https://picsum.photos/500/300?random=$index"
                                )
                            )
                        )
                    )
                )
            )
        }

        return NewsFeedResponseDto(
            newsFeedContent = NewsFeedContentDto(
                posts = posts,
                groups = groups,
                nextFrom = "mock_next_from_1"
            )
        )
    }

    private val recommendations: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    // Implementations

    override fun getAuthStateFlow(): StateFlow<AuthState> = authStateFlow

    val TAG = "Comments"

    override fun getRecommendations(): StateFlow<List<FeedPost>> = recommendations
    override fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>> = flow {
        Log.d(TAG, "getComments: getting comments...")
        var comments = apiService.getComments(
            accessToken = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        Log.d(TAG, "getComments: comments received $comments")

        if (comments.content == null ) {
            comments = mockCommentsResponse(
                postId = feedPost.id
            )
        }
        Log.d(TAG, "getComments: put mocks to comments $comments")
        emit(mapper.mapResponseToComments(comments))
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    fun mockCommentsResponse(postId: Long): CommentsResponseDto {
        val profiles = listOf(
            ProfileDto(
                id = 1L,
                firstName = "Иван",
                lastName = "Иванов",
                avatarUrl = "https://i.pravatar.cc/100?img=1"
            ),
            ProfileDto(
                id = 2L,
                firstName = "Мария",
                lastName = "Петрова",
                avatarUrl = "https://i.pravatar.cc/100?img=2"
            ),
            ProfileDto(
                id = 3L,
                firstName = "Алексей",
                lastName = "Сидоров",
                avatarUrl = "https://i.pravatar.cc/100?img=3"
            )
        )

        val comments = (1..5).map { index ->
            CommentDto(
                id = index.toLong(),
                authorId = profiles.random().id,
                text = "Это моковый комментарий №$index к посту $postId",
                date = System.currentTimeMillis() / 1000 - index * 60L // каждый минус одна минута
            )
        }

        return CommentsResponseDto(
            content = CommentsContentDto(
                comments = comments,
                profiles = profiles
            )
        )
    }

    override suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    override suspend fun checkAuthState() {
        checkAuthStateEvents.emit(Unit)
    }

    override suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(
            accessToken = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        }
        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, newLikesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
        refreshedListFlow.emit(feedPosts)
    }

    companion object {

        private const val RETRY_TIMEOUT_MILLIS = 3000L
    }
}
