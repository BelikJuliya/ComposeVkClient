package com.example.composeapp.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.composeapp.di.ApplicationComponent
import com.example.composeapp.di.DaggerApplicationComponent
import com.vk.api.sdk.VK
import com.vk.id.VKID
import java.util.Locale

class VkNewsApp: Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    Log.d("RECOMPOSITION_TAG", "getApplicationComponent")
    return (LocalContext.current.applicationContext as VkNewsApp).component
}