package com.example.composeapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.internal.HttpMultipartEntry
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthCallback
import com.vk.id.auth.VKIDAuthParams

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ComposeAppTheme {
                val viewModel: MainViewModel = viewModel()
                val authState = viewModel.authState.observeAsState(AuthState.Initial)

                when (authState.value) {
                    is AuthState.Authorized -> {
                        MainScreen()
                    }

                    is AuthState.NotAuthorized -> {
//                        LoginScreen {
                        SideEffect {
                            VKID.instance.authorize(
                                lifecycleOwner = this,
                                callback = object : VKIDAuthCallback {
                                        override fun onAuth(accessToken: AccessToken) {
                                            viewModel.performAuthResult(AuthState.Authorized(accessToken))
                                        }

                                        override fun onFail(fail: VKIDAuthFail) {
                                            viewModel.performAuthResult(AuthState.NotAuthorized)
                                        }

                                    },
                                params = VKIDAuthParams {
                                    scopes = setOf("wall")
                                }
                            )
                        }
                    }

                    else -> {

                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onLoginClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { onLoginClick() }) {
            HttpMultipartEntry.Text("Войти через VK")
        }
    }
}
