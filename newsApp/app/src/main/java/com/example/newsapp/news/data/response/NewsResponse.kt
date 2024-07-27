package com.example.newsapp.news.data.response

data class NewsResponse(
    val available: Int,
    val news: List<New>,
    val number: Int,
    val offser: Int
)
