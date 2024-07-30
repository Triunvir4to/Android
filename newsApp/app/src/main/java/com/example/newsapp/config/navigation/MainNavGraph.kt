package com.example.newsapp.config.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.newsapp.app.home.presentation.Home
import com.example.newsapp.app.home.presentation.HomeScreen
import com.example.newsapp.app.news.data.model.News
import com.example.newsapp.app.news.presentation.NewsDetails
import com.example.newsapp.app.news.presentation.NewsDetailsScreen
import kotlin.reflect.typeOf

@Composable
fun MainNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = HomeScreen
    ) {
        composable<HomeScreen> {
            Home(
                navController = navHostController
            )
        }

        composable<NewsDetailsScreen>(
            typeMap = NewsDetailsScreen.typeMap
        ) {
            val newsDetailsScreen: NewsDetailsScreen = it.toRoute<NewsDetailsScreen>()

            NewsDetails(
                navController = navHostController,
                news = newsDetailsScreen.news
            )
        }
    }
}