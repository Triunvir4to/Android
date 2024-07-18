package com.example.jetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcompose.screens.DetailScreen
import com.example.jetpackcompose.screens.HomeScreen

sealed class Screen(
    val route: String,
    val content: @Composable (navController: NavController) -> Unit
) {
    data object Home : Screen(
        route = "home",
        content = {navController ->  HomeScreen(navController) }
    )

    data object Detail : Screen(
        route = "detail",
        content = { navController -> DetailScreen(navController) }
    )
}