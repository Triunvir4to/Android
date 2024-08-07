package com.example.newsapp.app.resources.bookmarks.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsapp.app.resources.news.presentation.components.NewsListComponent

object BookmarkScreenData

@Composable
fun BookmarkScreen(
    navController: NavController
) {
    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()
    val bookmarks = bookmarkViewModel.getBookmarks().collectAsState(initial = emptyList())

    Column {
        Text(
            text = "Favoritos",
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.size(16.dp))

        NewsListComponent(
            navController = navController,
            news = bookmarks.value
        )
    }
}