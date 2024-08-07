package com.example.newsapp.app.layouts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.newsapp.app.components.BottomBar
import com.example.newsapp.config.navigation.main.MainNavGraph
import com.example.newsapp.config.navigation.main.screen.NavigableScreen

@Composable
fun MainLayout(
    navController: NavHostController,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomBar(
                navController = navController,
                screens = listOf(
                    NavigableScreen.Home,
                    NavigableScreen.Bookmark
                )
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            MainNavGraph(navHostController = navController)
        }
    }
}