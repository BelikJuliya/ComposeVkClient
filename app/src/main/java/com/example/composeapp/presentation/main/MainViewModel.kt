package com.example.composeapp.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.id.AccessToken

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _authState = MutableLiveData<AuthState>(AuthState.NotAuthorized)
    val authState: LiveData<AuthState> = _authState

//    init {
//        val storage = VKPreferencesKeyValueStorage(application)
//        val token = VKAccessToken.restore(storage)
//        val loggedIn = token != null && token.isValid
//        _authState.value = if (loggedIn) AuthState.Authorized(token!!) else AuthState.NotAuthorized
//    }

    fun performAuthResult(result: AuthState) {
        when(result) {
            is AuthState.Authorized -> saveToken(result.accessToken)
            AuthState.Initial -> Unit
            AuthState.NotAuthorized -> Unit
        }
        _authState.value = result
    }

    private fun saveToken(accessToken: AccessToken) {

    }
}