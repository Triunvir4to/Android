package com.example.newsapp.app.news.di

import com.example.newsapp.app.news.data.repository.NewsRepository
import com.example.newsapp.app.news.domain.NewsApiCaller
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsModule {

    @Provides
    fun providesNewsApiCaller(): NewsApiCaller{
        return NewsApiCaller()
    }

    @Provides
    fun provideNewsRepository(api: NewsApiCaller): NewsRepository{
        return NewsRepository(api)
    }
}