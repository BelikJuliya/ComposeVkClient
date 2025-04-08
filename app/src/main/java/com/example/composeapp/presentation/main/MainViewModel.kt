package com.example.composeapp.presentation.main

import android.app.Application
import android.util.Log
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
import com.vk.id.auth.VKIDAuthParams
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val TAG = this.javaClass.simpleName

    private val _authState = MutableLiveData<AuthState>(AuthState.NotAuthorized)
    val authState: LiveData<AuthState> = _authState

    private val tokenStorage = SecureTokenStorage(application)

    init {
        val token = tokenStorage.getAccessToken()
        if (token != null) {
            _authState.value = AuthState.Authorized(token)
        } else {
            _authState.value = AuthState.NotAuthorized
        }
    }

    private val vkAuthCallback = object : VKIDAuthCallback {
        override fun onAuth(accessToken: AccessToken) {
            Log.d(TAG, "Bearer token = ${accessToken.token}")
            tokenStorage.saveAccessToken(accessToken)
            _authState.value = AuthState.Authorized(accessToken)
        }

        override fun onFail(fail: VKIDAuthFail) {
            _authState.value = AuthState.NotAuthorized
        }
    }

    fun authorize() = viewModelScope.launch {
        VKID.instance.authorize(vkAuthCallback, params = VKIDAuthParams {
            scopes = setOf("wall", "friends")
        })
    }
}