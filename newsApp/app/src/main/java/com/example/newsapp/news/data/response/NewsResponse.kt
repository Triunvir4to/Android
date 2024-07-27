package com.example.newsapp.news.data.response

import com.example.newsapp.news.data.model.News

data class NewsResponse(
    val available: Int,
    val news: List<News>,
    val number: Int,
    val offser: Int
)
