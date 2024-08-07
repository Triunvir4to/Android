package com.example.newsapp.app.resources.bookmarks.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.app.resources.news.presentation.components.NewsListComponent
import kotlinx.serialization.Serializable

@Serializable
object BookmarkScreenData

@Composable
fun BookmarkScreen(
    navController: NavController
) {
    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()
    val bookmarks = bookmarkViewModel.getBookmarks().collectAsState(initial = emptyList())

    Column {
        Text(
            modifier = Modifier
                .padding(vertical = 32.dp),
            text = "Favoritos",
            style = MaterialTheme.typography.titleLarge,
        )

        NewsListComponent(
            navController = navController,
            news = bookmarks.value
        )
    }
}
@Preview
@Composable
private fun BookmarkScreenPreview(){
    BookmarkScreen(navController = rememberNavController())
}