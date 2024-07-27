package com.example.newsapp.api.di

import com.example.newsapp.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(
        okHttpClient: OkHttpClient
    ): ApiService {
        return ApiService(
            baseOkHttpClient = okHttpClient,
            exceptionRecorder = { _ -> },
            logger = Logger.DEFAULT
        )
    }
}