package com.example.composeapp.presentation.main

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences

import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.vk.id.AccessToken
import androidx.core.content.edit

class SecureTokenStorage(context: Context) {

    private val gson = Gson()
    private val prefs = EncryptedSharedPreferences.create(
        "secure_token_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveAccessToken(token: AccessToken) {
        val json = gson.toJson(token)
        prefs.edit { putString("access_token", json) }
    }

    fun getAccessToken(): AccessToken? {
        val json = prefs.getString("access_token", null) ?: return null
        return try {
            gson.fromJson(json, AccessToken::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun clearAccessToken() {
        prefs.edit { remove("access_token") }
    }
}
