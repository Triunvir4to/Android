package com.example.newsapp.app.news.domain.repository

import com.example.newsapp.api.ApiResponse
import com.example.newsapp.app.news.data.response.NewsResponse
import com.example.newsapp.app.news.domain.NewsApiCaller

interface INewsRepository {
    val apiCaller: NewsApiCaller
    suspend fun getNews(
        language: String = apiCaller.defaultLanguage,
        text: String? = null,
        country: String? = null
    ): ApiResponse<NewsResponse>
}