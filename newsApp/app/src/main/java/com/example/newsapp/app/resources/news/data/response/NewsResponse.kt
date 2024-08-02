package com.example.newsapp.app.resources.news.data.response

import com.example.newsapp.app.resources.news.data.model.News
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val available: Int,
    val news: List<News>,
    val number: Int,
    val offset: Int
)
