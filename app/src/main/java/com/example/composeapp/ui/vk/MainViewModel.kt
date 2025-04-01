package com.example.composeapp.ui.vk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeapp.domain.FeedPost
import com.example.composeapp.domain.StatisticItem

class MainViewModel : ViewModel() {

    private val _feedPost = MutableLiveData(FeedPost())
    val feedPost: LiveData<FeedPost> = _feedPost

    fun updateCount(newItem: StatisticItem) {
        val oldStatistics = feedPost.value?.statistics ?: throw IllegalStateException()
        val newStatistics = oldStatistics.map { oldItem ->
            if (oldItem.type == newItem.type) {
                oldItem.copy(count = oldItem.count + 1)
            } else {
                oldItem
            }
        }

        _feedPost.value = feedPost.value?.copy(statistics = newStatistics)
    }
}