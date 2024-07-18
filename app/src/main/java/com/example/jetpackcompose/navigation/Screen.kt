package com.example.jetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.jetpackcompose.screens.DetailScreen
import com.example.jetpackcompose.screens.HomeScreen
import kotlinx.serialization.Serializable

sealed class Screen(
    val route: String,
    val content: @Composable (navController: NavController) -> Unit
) {
    @Serializable
    data object Home : Screen(
        route = "home",
        content = { navController -> HomeScreen(navController) }
    )

    @Serializable
    data object Detail : Screen(
        route = "detail",
        content = { navController -> DetailScreen(navController) }
    )
}