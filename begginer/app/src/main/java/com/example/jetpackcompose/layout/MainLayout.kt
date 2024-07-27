package com.example.jetpackcompose.layout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.jetpackcompose.components.BottomBar
import com.example.jetpackcompose.navigation.MainNavGraph
import com.example.jetpackcompose.screens.Screen

@Composable
fun MainLayout(
    navController: NavHostController,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
    ) { it.calculateTopPadding(); MainNavGraph(navHostController = navController) }
}