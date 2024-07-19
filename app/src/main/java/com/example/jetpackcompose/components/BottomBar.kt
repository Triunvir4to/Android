package com.example.jetpackcompose.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetpackcompose.screens.DetailScreen
import com.example.jetpackcompose.screens.HomeScreen
import com.example.jetpackcompose.screens.Screen
import com.example.jetpackcompose.screens.SettingsScreen

@Composable
fun RowScope.AddItem(
    currentScreenTitle: MutableState<String>,
    screen: Screen<out Any>,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    currentDestination?.hierarchy?.let {
        NavigationBarItem(
            label = {
                Text(text = screen.title)
            },
            icon = {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = "${screen.title} Navigation Icon"
                )
            },
            selected = it.any {
                currentScreenTitle.value == screen.title
            },
            onClick = {
                currentScreenTitle.value = screen.title
                when (screen) {
                    is Screen.Home -> {
                        navController.navigate(
                            HomeScreen
                        ){
                            launchSingleTop = true
                        }
                    }

                    is Screen.Profile -> {
                        navController.navigate(
                            DetailScreen(
                                id = 30
                            )
                        ){
                            launchSingleTop = true
                        }
                    }

                    is Screen.Settings -> {
                        navController.navigate(
                            SettingsScreen
                        ){
                            launchSingleTop = true
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    screens: List<Screen<out Any>>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestinantion = navBackStackEntry?.destination

    NavigationBar {
        val currentScreenTitle = remember { mutableStateOf("Home") }
        screens.forEach { screen ->
            AddItem(
                currentScreenTitle = currentScreenTitle,
                screen = screen,
                currentDestination = currentDestinantion,
                navController = navController
            )
        }
    }
}