package com.example.newsapp.config.di

import android.content.Context
import com.example.newsapp.NewsApp
import com.example.newsapp.services.env.EnvReader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideApplication(
        @ApplicationContext context: Context
    ): NewsApp {
        return context as NewsApp
    }

    @Singleton
    @Provides
    fun provideEnvReader(
        @ApplicationContext context: Context
    ): EnvReader {
        return EnvReader(context)
    }
}