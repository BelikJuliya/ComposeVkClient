package com.example.composeapp.data.model

import com.google.gson.annotations.SerializedName

data class GroupDto(
    val id: Long,
    val name: String,
    @SerializedName("photo_200") val imageUrl: String
)