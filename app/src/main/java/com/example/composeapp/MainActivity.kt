package com.example.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composeapp.ui.vk.MainScreen
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.example.composeapp.ui.vk.news.NewsFeedViewModel
import com.example.composeapp.ui.vk.comments.CommentsViewModel
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
