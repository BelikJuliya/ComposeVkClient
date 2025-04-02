package com.example.composeapp.ui.vk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeapp.domain.FeedPost
import com.example.composeapp.domain.StatisticItem
import java.util.Date

class MainViewModel : ViewModel() {

    private val initialList = mutableListOf<FeedPost>().apply {
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

    private val _feedPosts = MutableLiveData<List<FeedPost>>(initialList)
    val feedPost: LiveData<List<FeedPost>> = _feedPosts

    fun updateCount(statistic: StatisticItem, model: FeedPost) {
        val oldPosts = feedPost.value?.toMutableList() ?: mutableListOf()
        val oldStatistics = model.statistics
        val newStatistics = oldStatistics.map { oldItem ->
            if (oldItem.type == statistic.type) {
                oldItem.copy(count = oldItem.count + 1)
            } else {
                oldItem
            }
        }
        val newFeedPost = model.copy(statistics = newStatistics)

        _feedPosts.value = oldPosts.map {
            if (it.id == model.id) {
                newFeedPost
            } else it
        }
    }

    fun deleteItem(model: FeedPost) {
        val newItems = feedPost.value?.toMutableList() ?: mutableListOf()
        newItems.remove(model)
        _feedPosts.value = newItems
    }
}