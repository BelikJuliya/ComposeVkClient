package com.example.composeapp.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeapp.domain.FeedPost
import com.example.composeapp.domain.StatisticItem
import java.util.Date

class NewsFeedViewModel : ViewModel() {

    private val sourceList = mutableListOf<FeedPost>().apply {
        repeat(50) {
            add(
                FeedPost(
                    id = it,
                    communityName = "/dev/null № $it",
                    publicationDate = String.format("dd:MMM:yyyy", Date())
                )
            )
        }
    }

    private val initialState = NewsFeedScreenState.Posts(posts = sourceList)

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState


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