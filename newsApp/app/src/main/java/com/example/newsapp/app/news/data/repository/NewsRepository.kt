package com.example.newsapp.app.news.data.repository

import com.example.newsapp.api.ApiResponse
import com.example.newsapp.app.news.data.response.NewsResponse
import com.example.newsapp.app.news.domain.NewsApiCaller
import com.example.newsapp.app.news.domain.repository.INewsRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class NewsRepository @Inject constructor(
    override val apiCaller: NewsApiCaller
): INewsRepository {
    override suspend fun getNews(
        language: String,
        text: String?,
        country: String?
    ): ApiResponse<NewsResponse> {
        return apiCaller.getNews(
            country = country,
            context = Dispatchers.Default
        )
    }

}