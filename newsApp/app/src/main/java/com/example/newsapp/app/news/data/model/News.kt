package com.example.newsapp.app.news.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class News(
    val authors: List<String>,
    val id: Int,
    val image: String,
    val language: String,
    @SerialName("publish_date")
    val publishDate: String,
    val sentiment: Double,
    @SerialName("source_country")
    val sourceCountry: String,
    val sumary: String,
    val text: String,
    val title: String,
    val url: String
)
