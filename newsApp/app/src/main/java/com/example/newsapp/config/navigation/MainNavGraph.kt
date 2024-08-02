package com.example.newsapp.config.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.newsapp.app.resources.bookmarks.presentation.Bookmark
import com.example.newsapp.app.resources.bookmarks.presentation.BookmarkScreen
import com.example.newsapp.app.resources.home.presentation.HomeScreen
import com.example.newsapp.app.resources.home.presentation.HomeScreenData
import com.example.newsapp.app.resources.news.data.model.News
import com.example.newsapp.app.resources.news.presentation.NewsDetailsScreen

@Composable
fun MainNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = HomeScreenData
    ) {
        composable<HomeScreenData> {
            HomeScreen(
                navController = navHostController
            )
        }

        composable<News> {
            val news: News = it.toRoute<News>()

            NewsDetailsScreen(
                navController = navHostController,
                news = news
            )
        }

        composable<Bookmark> {
            BookmarkScreen(
                navController = navHostController
            )
        }

    }
}