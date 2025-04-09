package com.example.composeapp.presentation.news

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.data.mapper.NewsFeedMapper
import com.example.composeapp.data.network.ApiFactory
import com.example.composeapp.domain.FeedPost
import com.example.composeapp.domain.StatisticItem
import com.example.composeapp.presentation.main.SecureTokenStorage
import com.vk.api.sdk.VK
import com.vk.id.vksdksupport.withVKIDToken
import com.vk.sdk.api.base.dto.BaseUserGroupFieldsDto
import com.vk.sdk.api.friends.FriendsService
import com.vk.sdk.api.newsfeed.NewsfeedService
import com.vk.sdk.api.users.dto.UsersFieldsDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class NewsFeedViewModel : ViewModel() {

    private val sourceList = mutableListOf<FeedPost>().apply {
        repeat(50) {
            add(
                FeedPost(
                    id = "id$it"
                )
            )
        }
    }

    private val initialState = NewsFeedScreenState.Posts(posts = sourceList)

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

//    private val storage = SecureTokenStorage(application)

    private val mapper = NewsFeedMapper

    init {
        loadRecommendations()
    }

    private fun loadRecommendations() {
//        val token = storage.getAccessToken()?.token ?: return
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val test = VK.executeSync(
                    NewsfeedService()
                        .newsfeedGetRecommended()
                        .withVKIDToken(),
                )
                Log.d(this.javaClass.simpleName, test.toString())
            } catch (ex: Exception) {
                println(ex)

            }

//            val response = ApiFactory.apiService.loadRecommendations(token)
//            _screenState.value = NewsFeedScreenState.Posts(posts = mapper.mapResponseToPosts(response))
        }
    }

    fun updateCount(statistic: StatisticItem, model: FeedPost) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return
        val oldPosts = currentState.posts.toMutableList()
        val oldStatistics = model.statistics
        val newStatistics = oldStatistics.map { oldItem ->
            if (oldItem.type == statistic.type) {
                oldItem.copy(count = oldItem.count + 1)
            } else {
                oldItem
            }
        }
        val newFeedPost = model.copy(statistics = newStatistics)

        val newPosts = oldPosts.map {
            if (it.id == model.id) {
                newFeedPost
            } else it
        }

        _screenState.value = NewsFeedScreenState.Posts(posts = newPosts)
    }

    fun deleteItem(model: FeedPost) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val newItems = currentState.posts.toMutableList()
        newItems.remove(model)
        _screenState.value = NewsFeedScreenState.Posts(posts = newItems)
    }
}