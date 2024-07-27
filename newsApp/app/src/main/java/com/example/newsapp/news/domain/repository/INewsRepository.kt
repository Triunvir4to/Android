package com.example.newsapp.news.domain.repository

import com.example.newsapp.api.ApiResponse
import com.example.newsapp.news.data.response.NewsResponse

interface INewsRepository {
    suspend fun getNews(
        language: String,
        text: String?,
        country: String
    ): ApiResponse<NewsResponse>
}