package com.example.composeapp.presentation.comments

import com.example.composeapp.domain.FeedPost
import com.example.composeapp.domain.PostComment

sealed class CommentsScreenState {
    data object Idle : CommentsScreenState()
    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ) : CommentsScreenState()
}
