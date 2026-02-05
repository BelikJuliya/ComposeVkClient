package com.example.composeapp.presentation.comments

import com.example.composeapp.domain.model.FeedPost
import com.example.composeapp.domain.model.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ) : CommentsScreenState()
}

