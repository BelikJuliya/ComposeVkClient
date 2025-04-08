package com.example.composeapp.presentation

import android.app.Application
import com.vk.api.sdk.VK
import com.vk.id.VKID
import java.util.Locale

class VkNewsApp: Application() {
    override fun onCreate() {
        super.onCreate()
        VKID.init(this)
        VKID.instance.setLocale(Locale("ru"))
    }
}