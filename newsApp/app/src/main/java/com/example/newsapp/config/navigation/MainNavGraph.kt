package com.example.newsapp.config.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsapp.app.home.presentation.Home
import com.example.newsapp.app.home.presentation.HomeScreen

@Composable
fun MainNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = HomeScreen,
    ) {
        composable<HomeScreen> {
            Home()
        }
    }
}