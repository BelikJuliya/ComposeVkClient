package com.example.composeapp.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthCallback
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _authState = MutableLiveData<AuthState>(AuthState.NotAuthorized)
    val authState: LiveData<AuthState> = _authState

    private val vkAuthCallback = object : VKIDAuthCallback {
        override fun onAuth(accessToken: AccessToken) {
            _authState.value = AuthState.Authorized(accessToken)
        }

        override fun onFail(fail: VKIDAuthFail) {
            _authState.value = AuthState.NotAuthorized
        }
    }

    fun authorize() = viewModelScope.launch {
        VKID.instance.authorize(vkAuthCallback)
    }

    fun performAuthResult(result: AuthState) {
        when (result) {
            is AuthState.Authorized -> saveToken(result.accessToken)
            AuthState.Initial -> Unit
            AuthState.NotAuthorized -> Unit
        }
        _authState.value = result
    }

    private fun saveToken(accessToken: AccessToken) {

    }
}