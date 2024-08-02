package com.example.newsapp.app.resources.news.domain.repository

import com.example.newsapp.services.api.utils.ApiResponse
import com.example.newsapp.app.resources.news.data.response.NewsResponse
import com.example.newsapp.app.resources.news.domain.NewsApiCaller
import kotlin.coroutines.CoroutineContext

interface INewsRepository {
    val apiCaller: NewsApiCaller
    suspend fun getNews(
        language: String? = null,
        text: String? = null,
        country: String? = null,
        context: CoroutineContext
    ): ApiResponse<NewsResponse>
}