package com.example.newsapp.app.news.presentation

import androidx.compose.runtime.Composable
import com.example.newsapp.app.news.data.model.News
import kotlinx.serialization.Serializable

@Serializable
data class NewsDetailsScreen(
    val news: News
)

@Composable
fun NewsDetails(
    news: News
){

}