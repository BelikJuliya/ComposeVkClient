package com.example.composeapp.presentation.main

import com.vk.id.AccessToken

sealed class AuthState {
    data class Authorized(val accessToken: AccessToken): AuthState()
    object NotAuthorized: AuthState()
    object Initial: AuthState()
}