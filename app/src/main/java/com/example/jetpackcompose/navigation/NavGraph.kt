package com.example.jetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.jetpackcompose.screens.Detail
import com.example.jetpackcompose.screens.DetailScreen
import com.example.jetpackcompose.screens.Home
import com.example.jetpackcompose.screens.HomeScreen
import com.example.jetpackcompose.screens.Settings
import com.example.jetpackcompose.screens.SettingsScreen

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = HomeScreen,
    ) {
        composable<HomeScreen> {
            Home(navHostController)
        }

        composable<DetailScreen> {
            Detail(navHostController, it.toRoute<DetailScreen>().id)
        }

        composable<SettingsScreen> {
            Settings(navHostController)
        }
    }
}