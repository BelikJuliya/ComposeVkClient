package com.example.composeapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.SideEffect
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ComposeAppTheme {
                val authLauncher = rememberLauncherForActivityResult(contract = VK.getVKAuthActivityResultContract()) {
                    VK.login(this) { result: VKAuthenticationResult ->
                        when (result) {
                            is VKAuthenticationResult.Success -> {
                                // User passed authorization
                            }

                            is VKAuthenticationResult.Failed -> {
                                // User didn't pass authorization
                            }
                        }
                    }
                }
                SideEffect { authLauncher.launch(arrayListOf(VKScope.WALL)) }
                MainScreen()
            }
        }
    }
}
