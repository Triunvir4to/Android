package com.example.newsapp.news.data.response

import com.example.newsapp.news.data.model.News
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val available: Int,
    val news: List<News>,
    val number: Int,
    val offser: Int
)
