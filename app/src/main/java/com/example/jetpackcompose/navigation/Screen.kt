package com.example.jetpackcompose.navigation

import androidx.compose.runtime.Composable
import com.example.jetpackcompose.screens.DetailScreen
import com.example.jetpackcompose.screens.HomeScreen

sealed class Screen(
    val route: String,
    val content: @Composable () -> Unit
) {
    data object Home : Screen(
        route = "home",
        content = { HomeScreen() }
    )

    data object Detail : Screen(
        route = "detail",
        content = { DetailScreen() }
    )
}