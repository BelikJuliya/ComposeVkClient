package com.example.composeapp.presentation.news

import com.example.composeapp.domain.FeedPost

sealed class NewsFeedScreenState {

    data object Idle: NewsFeedScreenState()

    data class Posts(
        val posts: List<FeedPost>
    ) : NewsFeedScreenState()
}