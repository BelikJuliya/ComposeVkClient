package com.example.composeapp.ui.vk.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.composeapp.domain.PostComment
import com.example.composeapp.ui.vk.BaseViewModel

class CommentsViewModel :
    BaseViewModel<PostComment>(itemFactory = { index ->
        PostComment(
            id = index
        )
    }) {

//    private val _comments = MutableLiveData<List<PostComment>>(initialList)
//    val comments: LiveData<List<PostComment>> = _comments

//    fun deleteItem(model: PostComment) {
//        val newItems = _comments.value?.toMutableList() ?: mutableListOf()
//        newItems.remove(model)
//        _comments.value = newItems
//    }
}