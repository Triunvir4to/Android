package com.example.newsapp.app.components

import androidx.compose.foundation.Image
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination.Companion.hierarchy
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
        val currentDestination = navBackStackEntry?.destination
        val currentScreenTitle = remember { mutableStateOf("Home") }

        screens.forEach { screen ->
            currentDestination?.hierarchy?.let {
                NavigationBarItem(
                    selected = it.any {
                        currentScreenTitle.value == screen.title
                    },
                    onClick = {
                        currentScreenTitle.value = screen.title
                        navController.navigate(screen.identifier)
                    },
                    icon = {
                        Image(
                            imageVector = screen.icon,
                            contentDescription = "${screen.title} Icon"
                        )
                    },
                    label = {
                        Text(screen.title)
                    }
                )
            }
        }
    }
}