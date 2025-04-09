package com.example.composeapp.data.model

import com.google.gson.annotations.SerializedName

data class PostDto(
    val id: String,
    @SerializedName("source_id") val communityId: Long,
    @SerializedName("is_favourite") val isFavourite: Boolean,
    val text: String,
    val date: Long,
    val likes: LikesDto,
    val comments: CommentsDto,
    val views: ViewsDto,
    val reposts: RepostsDto,
    val attachments: List<AttachmentDto>?
)
