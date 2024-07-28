package com.example.newsapp.app.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.app.news.data.model.News
import com.example.newsapp.app.news.presentation.components.NewsItemComponent
import com.example.newsapp.app.ui.theme.Shapes
import com.example.newsapp.services.api.utils.ApiResponse

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
private fun Fail(
    error: ApiResponse.Error,
    viewModel: HomeViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Failed")
        Text(text = error.message)
        Button(onClick = { viewModel.getNews() }) {
            Text(text = "Retry")
        }
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
private fun SearchBar(
    text: String,
    onSearch: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = onSearch,
            label = { Text(text = "Pesquisa") },
            shape = Shapes.extraLarge,
        )

        Image(
            painterResource(id = android.R.drawable.ic_menu_search),
            contentDescription = "Search Icon",
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState = homeViewModel.state.collectAsState()
    val searchText = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        SearchBar(text = searchText.value) {
            searchText.value = it
        }

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        when (val response = uiState.value) {
            is ApiResponse.Loading -> {
                Loading()
            }

            is ApiResponse.Fail -> {
                val error = response.error
                Fail(
                    error = error,
                    viewModel = homeViewModel
                )
            }

            is ApiResponse.Success -> {
                val news = response.response.body.news
                Success(news = news)
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}