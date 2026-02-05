package com.example.composeapp.domain.usecase

import com.example.composeapp.domain.repoository.NewsFeedRepository
import com.example.composeapp.domain.model.FeedPost
import javax.inject.Inject

class ChangeLikeStatusUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke(feedPost: FeedPost) {
        repository.changeLikeStatus(feedPost)
    }
}
