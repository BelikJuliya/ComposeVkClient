package com.example.composeapp.presentation.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeapp.domain.FeedPost
import com.example.composeapp.domain.PostComment

class CommentsViewModel(
    feedPost: FeedPost
) : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Idle)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        loadComments(feedPost)
    }

    private fun loadComments(feedPost: FeedPost) {
        val comments = mutableListOf<PostComment>().apply {
            repeat(15) {
                add(PostComment(id = it))
            }
        }
        _screenState.value = CommentsScreenState.Comments(feedPost = feedPost, comments = comments)
    }
}