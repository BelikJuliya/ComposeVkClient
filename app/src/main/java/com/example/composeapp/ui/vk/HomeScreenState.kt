package com.example.composeapp.ui.vk

import com.example.composeapp.domain.FeedPost
import com.example.composeapp.domain.PostComment

sealed class HomeScreenState {

    data object Idle: HomeScreenState()

    data class Posts(
        val posts: List<FeedPost>
    ) : HomeScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ) : HomeScreenState()
}