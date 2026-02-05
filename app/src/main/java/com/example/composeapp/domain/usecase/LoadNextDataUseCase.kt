package com.example.composeapp.domain.usecase

import android.util.Log
import com.example.composeapp.domain.repoository.NewsFeedRepository
import javax.inject.Inject

class LoadNextDataUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke() {
        val nextData =repository.loadNextData()
        Log.d("LoadNextDataUseCase", "nextData = $nextData")
    }
}
