package com.example.composeapp.ui.vk.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.composeapp.domain.FeedPost
import com.example.composeapp.domain.PostComment
import com.example.composeapp.ui.vk.BaseViewModel
import com.example.composeapp.ui.vk.news.NewsFeedScreenState

class CommentsViewModel :
    BaseViewModel<PostComment>(itemFactory = { index ->
        PostComment(
            id = index
        )
    }) {

    init {
        loadComments(FeedPost())
    }

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Idle)
    val screenState: LiveData<CommentsScreenState> = _screenState

//    private var savedState: NewsFeedScreenState? = initialState

    fun loadComments(feedPost: FeedPost) {
        val comments = mutableListOf<PostComment>().apply {
            repeat(15) {
                add(PostComment(id = it))
            }
        }
        _screenState.value = CommentsScreenState.Comments(feedPost = feedPost, comments = comments)
    }

//    fun showComments(feedPost: FeedPost) {
//        savedState = screenState.value
//        _screenState.value = NewsFeedScreenState.Comments(feedPost = feedPost, comments = comments)
//    }
//
//    fun closeComments() {
//        savedState?.let { _screenState.value = it }
//    }
}