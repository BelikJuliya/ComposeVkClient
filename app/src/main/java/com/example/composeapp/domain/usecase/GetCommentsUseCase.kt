package com.example.composeapp.domain.usecase

import com.example.composeapp.domain.repoository.NewsFeedRepository
import com.example.composeapp.domain.model.FeedPost
import com.example.composeapp.domain.model.PostComment
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(feedPost: FeedPost): StateFlow<List<PostComment>> {
        return repository.getComments(feedPost)
    }
}
