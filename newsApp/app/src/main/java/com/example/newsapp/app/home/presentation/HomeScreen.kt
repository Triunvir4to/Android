package com.example.newsapp.app.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.api.ApiResponse
import com.example.newsapp.app.news.data.model.News
import com.example.newsapp.app.news.presentation.components.NewsItemComponent

@Composable
private fun Loading() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text(text = "Loading...")
    }
}

@Composable
private fun Fail(error: ApiResponse.Error) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Failed")
        Text(text = error.message)
    }
}

@Composable
private fun Success(news: List<News>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Text(text = "Notícias")
        }
        items(news) { article ->
            NewsItemComponent(article)
        }
    }
}

@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState = homeViewModel.state.collectAsState()

    when (val response = uiState.value) {
        is ApiResponse.Loading -> {
            Loading()
        }

        is ApiResponse.Fail -> {
            val error = response.error
            Fail(error = error)
        }

        is ApiResponse.Success -> {
            val news = response.response.body.news
            Success(news = news)
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}