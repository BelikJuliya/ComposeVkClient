package com.example.composeapp.ui.vk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeapp.domain.FeedPost
import com.example.composeapp.domain.StatisticItem
import java.util.Date

class MainViewModel : ViewModel() {

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

    private val initialState = HomeScreenState.Posts(posts = sourceList)

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState> = _screenState

    fun updateCount(statistic: StatisticItem, model: FeedPost) {
        val oldPosts = screenState.value?.toMutableList() ?: mutableListOf()
        val oldStatistics = model.statistics
        val newStatistics = oldStatistics.map { oldItem ->
            if (oldItem.type == statistic.type) {
                oldItem.copy(count = oldItem.count + 1)
            } else {
                oldItem
            }
        }
        val newFeedPost = model.copy(statistics = newStatistics)

        _screenState.value = oldPosts.map {
            if (it.id == model.id) {
                newFeedPost
            } else it
        }
    }

    fun deleteItem(model: FeedPost) {
        val newItems = screenState.value?.toMutableList() ?: mutableListOf()
        newItems.remove(model)
        _screenState.value = newItems
    }
}