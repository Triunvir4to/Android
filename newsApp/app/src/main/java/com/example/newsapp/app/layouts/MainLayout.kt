package com.example.newsapp.app.layouts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.newsapp.config.navigation.MainNavGraph

@Composable
fun MainLayout(
    navController: NavHostController,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { it.calculateTopPadding(); MainNavGraph(navHostController = navController) }
}