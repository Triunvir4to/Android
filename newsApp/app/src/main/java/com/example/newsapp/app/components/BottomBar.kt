package com.example.newsapp.app.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.newsapp.services.screen.BottomBarScreen

@Composable
fun BottomBar(
    navController: NavHostController,
    screens: List<BottomBarScreen>
) {
    BottomAppBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestinantion = navBackStackEntry?.destination
        screens.forEach(

        )
    }
}