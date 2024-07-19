package com.example.jetpackcompose.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.jetpackcompose.components.BottomBar
import com.example.jetpackcompose.navigation.MainNavGraph
import com.example.jetpackcompose.screens.Screen

@Composable
fun MainLayout(
    navController: NavHostController,
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                screens = listOf(
                    Screen.Home,
                    Screen.Profile,
                    Screen.Settings
                )
            )
        }
    ) {
        MainNavGraph(navHostController = navController)
        content(it)
    }
}