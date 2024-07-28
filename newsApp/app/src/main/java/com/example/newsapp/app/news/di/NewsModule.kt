package com.example.newsapp.app.news.di

import com.example.newsapp.services.api.config.ApiService
import com.example.newsapp.app.news.data.repository.NewsRepository
import com.example.newsapp.app.news.domain.NewsApiCaller
import com.example.newsapp.services.env.EnvReader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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
}