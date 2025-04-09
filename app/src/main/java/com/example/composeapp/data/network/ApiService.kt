package com.example.composeapp.data.network

import com.example.composeapp.data.model.NewsFeedContentDto
import com.example.composeapp.data.model.NewsFeedResponseDto

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun loadRecommendations(
        @Query("access_token") token: String
    ): NewsFeedContentDto
}