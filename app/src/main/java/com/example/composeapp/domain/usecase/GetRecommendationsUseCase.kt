package com.example.composeapp.domain.usecase

import android.util.Log
import com.example.composeapp.domain.repoository.NewsFeedRepository
import com.example.composeapp.domain.model.FeedPost
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetRecommendationsUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(): StateFlow<List<FeedPost>> {
        val flow = repository.getRecommendations()
        val list = flow.value.firstOrNull()
        Log.d("MainScreen", "GetRecommendationsUseCase: recommendations loaded $list")

        return flow
    }
}
