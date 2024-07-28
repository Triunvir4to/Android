package com.example.newsapp.app.news.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class News(
    val author: String?,
    val catgory: String? = null,
    val authors: List<String>? = null,
    val id: Int,
    val image: String,
    val video: String?,
    val language: String,
    @SerialName("publish_date")
    val publishDate: String,
    val sentiment: Double? = null,
    @SerialName("source_country")
    val sourceCountry: String,
    val summary: String,
    val text: String,
    val title: String,
    val url: String
)