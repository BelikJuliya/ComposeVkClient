package com.example.composeapp.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.composeapp.data.network.ApiService
import com.example.composeapp.data.network.NewsFeedRepositoryImpl
import com.example.composeapp.domain.repoository.NewsFeedRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository

    companion object {

//        @Provides
//        @ApplicationScope
//        fun provideContext(application: Application): Context {
//            return application
//        }

        @Provides
        @ApplicationScope
        fun provideOkHttpClient(
            context: Context
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .addInterceptor(ChuckerInterceptor(context))
                .build()
        }

        @Provides
        @ApplicationScope
        fun provideRetrofit(
            okHttpClient: OkHttpClient
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.vk.com/method/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        @Provides
        @ApplicationScope
        fun provideApiService(
            retrofit: Retrofit
        ): ApiService {
            return retrofit.create(ApiService::class.java)
        }


        @ApplicationScope
        @Provides
        fun provideVkStorage(
            context: Context
        ): VKPreferencesKeyValueStorage {
            return VKPreferencesKeyValueStorage(context)
        }
    }
}
