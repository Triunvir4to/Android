package com.example.jetpackcompose.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

sealed class Screen<T : Any>(
    val title: String,
    val identifier: KClass<@Serializable T>,
    val icon: ImageVector
) {
    data object Home : Screen<HomeScreen>(
        title = "Home",
        identifier = HomeScreen::class,
        icon = Icons.Default.Home
    )

    data object Profile : Screen<DetailScreen>(
        title = "Profile",
        identifier = DetailScreen::class,
        icon = Icons.Default.Person
    )

    data object Settings : Screen<SettingsScreen>(
        title = "Settings",
        identifier = SettingsScreen::class,
        icon = Icons.Default.Settings
    )
}