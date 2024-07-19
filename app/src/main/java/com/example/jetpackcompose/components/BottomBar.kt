package com.example.jetpackcompose.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetpackcompose.screens.DetailScreen
import com.example.jetpackcompose.screens.Screen

@Composable
fun <T : Any> RowScope.AddItem(
    screen: Screen<T>,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val currentScreenTitle = remember { mutableStateOf("Home") }
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
                    is Screen.Profile -> {
                        navController.navigate(
                            DetailScreen(
                                id = 30
                            )
                        )
                    }

                    else -> {
                        navController.navigate(
                            screen.identifier
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    screens: List<Screen<Any>>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestinantion = navBackStackEntry?.destination

    NavigationBar() {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestinantion,
                navController = navController
            )
        }
    }
}