package com.example.newsapp.api

import android.content.Context
import com.example.newsapp.NewsApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.classgraph.ClassGraph
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import okhttp3.OkHttpClient
import javax.inject.Singleton
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.isSuperclassOf

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideApplication(
        @ApplicationContext context: Context
    ): NewsApp {
        return context as NewsApp
    }

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