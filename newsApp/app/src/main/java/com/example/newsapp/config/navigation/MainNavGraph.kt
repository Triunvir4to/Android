package com.example.newsapp.config.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.newsapp.app.home.presentation.HomeScreen
import com.example.newsapp.app.home.presentation.HomeScreenData
import com.example.newsapp.app.news.data.model.News
import com.example.newsapp.app.news.presentation.NewsDetailsScreen

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

        composable<News>(
        ) {
            val news: News = it.toRoute<News>()

            NewsDetailsScreen(
                navController = navHostController,
                news = news
            )
        }
    }
}