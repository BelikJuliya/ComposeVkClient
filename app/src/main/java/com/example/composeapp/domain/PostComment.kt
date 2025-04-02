package com.example.composeapp.domain

import com.example.composeapp.R

data class PostComment(
    override val id: Int = 0,
    val authorName: String = "Author",
    val authorAvatarId: Int = R.drawable.me,
    val commentText: String = "Long comment text",
    val publicationDate: String = "14:00"
): BaseModel
