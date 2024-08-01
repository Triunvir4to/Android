package com.example.newsapp.app.news.di

import android.content.Context
import com.example.newsapp.app.news.data.database.NewsDatabase
import com.example.newsapp.app.news.data.repository.NewsRepository
import com.example.newsapp.app.news.domain.NewsApiCaller
import com.example.newsapp.services.api.config.ApiService
import com.example.newsapp.services.env.EnvReader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsModule {

    @Provides
    fun providesNewsApiCaller(
        envReader: EnvReader,
        apiService: ApiService
    ): NewsApiCaller {
        return NewsApiCaller(envReader, apiService)
    }

    @Provides
    fun provideNewsRepository(api: NewsApiCaller): NewsRepository {
        return NewsRepository(api)
    }

    @Provides
    @Singleton
    fun providesNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return NewsDatabase.getDatabase(context)
    }
}