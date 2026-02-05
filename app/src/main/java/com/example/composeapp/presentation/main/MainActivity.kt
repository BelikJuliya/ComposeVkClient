package com.example.composeapp.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeapp.presentation.getApplicationComponent
import com.example.composeapp.presentation.login.LoginScreen
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    val TAG = "MainScreen"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val component = getApplicationComponent()
            val viewModel: MainViewModel = viewModel(factory = component.getViewModelFactory())
            val authState = viewModel.authState.collectAsState(AuthState.Initial)
            val launcher = rememberLauncherForActivityResult(
                contract = VK.getVKAuthActivityResultContract()
            ) {
                viewModel.performAuthResult()
            }
            ComposeAppTheme {
                when (authState.value) {
                    is AuthState.Authorized -> {
                        Log.d(TAG, "onCreate: Authorized -> open Main Screen")
                        MainScreen()
                    }

                    is AuthState.NotAuthorized -> {
                        LoginScreen {
                            Log.d(TAG, "onCreate: NotAuthorized -> open auth launcher")
                            launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                        }
                    }

                    AuthState.Initial ->  Unit
                }
            }
        }
    }
}
