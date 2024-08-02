package com.example.newsapp.app.resources.news.data.repository

import com.example.newsapp.services.api.utils.ApiResponse
import com.example.newsapp.app.resources.news.data.response.NewsResponse
import com.example.newsapp.app.resources.news.domain.NewsApiCaller
import com.example.newsapp.app.resources.news.domain.repository.INewsRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NewsRepository @Inject constructor(
    override val apiCaller: NewsApiCaller
) : INewsRepository {
    override suspend fun getNews(
        language: String?,
        text: String?,
        country: String?,
        context: CoroutineContext
    ): ApiResponse<NewsResponse> {
        return if (language != null) apiCaller.getNews(
            language = language,
            country = country,
            context = context
        )
        else apiCaller.getNews(
            country = country,
            context = context
        )
    }

}