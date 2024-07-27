package com.example.newsapp.app.news.domain.usecase

import com.example.newsapp.api.ApiResponse
import com.example.newsapp.app.news.data.repository.NewsRepository
import com.example.newsapp.app.news.data.response.NewsResponse
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend fun invoke(
        language: String? = null,
        text: String? = null,
        country: String? = null,
        context: CoroutineContext,
    ): ApiResponse<NewsResponse> {
        return newsInvocation(
            language = language,
            text = text,
            country = country,
            context = context
        )
    }

    private suspend fun newsInvocation(
        language: String? = null,
        text: String? = null,
        country: String? = null,
        context: CoroutineContext
    ): ApiResponse<NewsResponse> {
        return if (language != null) {
            newsRepository.getNews(
                context = context,
                text = text,
                country = country,
                language = language
            )
        } else {
            newsRepository.getNews(
                context = context,
                text = text,
                country = country
            )
        }
    }
}