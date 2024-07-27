package com.example.newsapp.news.data.model

data class News(
    val authos: List<String>,
    val id: Int,
    val image: String,
    val language: String,
    val publishDate: String,
    val sentiment: Double,
    val sourceCountry: String,
    val sumary: String,
    val text: String,
    val title: String,
    val url: String
)
