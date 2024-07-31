package com.example.newsapp.app.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.app.news.data.model.News
import com.example.newsapp.app.news.presentation.components.NewsItemComponent
import com.example.newsapp.app.news.presentation.components.NewsSkeletonLoader
import com.example.newsapp.services.api.utils.ApiResponse
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenData

@Composable
private fun Fail(
    error: ApiResponse.Error,
    searchText: String,
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
        Button(onClick = { viewModel.getNews(text = searchText) }) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun MainContentBackground(renderingItems: LazyListScope.() -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Text(text = "Notícias")
        }
        renderingItems()
    }
}

@Composable
private fun Loading() {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val skeletonHeight = 130.dp
    val count = (screenHeight / skeletonHeight).toInt()
    MainContentBackground {
        repeat(count) {
            item {
                NewsSkeletonLoader()
            }
        }
    }
}

@Composable
private fun Success(
    navController: NavController,
    news: List<News>
) {
    MainContentBackground {
        items(news) { article ->
            NewsItemComponent(
                news = article,
                onClick = {
                    navController
                        .navigate(article)
                }
            )
        }
    }
}

@Composable
private fun SearchBar(
    text: String,
    onSearch: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 1,
            value = text,
            onValueChange = onSearch,
            label = { Text(text = "Pesquisa") },
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(text)
                }
            ),
            trailingIcon = {
                IconButton(
                    onClick = { onSearch(text) }
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_search),
                        contentDescription = "Search Icon",
                        tint = Color.Unspecified
                    )
                }
            }
        )
    }
}

@Composable
fun HomeScreen(
    navController: NavController
) {
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
            homeViewModel.getNews(text = it)
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
                    searchText = searchText.value,
                    viewModel = homeViewModel
                )
            }

            is ApiResponse.Success -> {
                val news = response.response.body.news
                Success(
                    news = news,
                    navController = navController
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        rememberNavController()
    )
}