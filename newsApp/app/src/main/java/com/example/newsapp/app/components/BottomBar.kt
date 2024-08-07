package com.example.newsapp.app.components

import androidx.compose.foundation.Image
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
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
        val currentDestination = navBackStackEntry?.destination
        screens.forEach {
            NavigationBarItem(
                selected = currentDestination == it.identifier,
                onClick = {
                    navController.navigate(it.identifier)
                },
                icon = {
                    Image(
                        imageVector = it.icon,
                        contentDescription = "${it.title} Icon"
                    )
                },
                label = {
                    Text(it.title)
                }
            )
        }
    }
}