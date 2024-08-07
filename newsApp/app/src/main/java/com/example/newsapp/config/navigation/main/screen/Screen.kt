package com.example.newsapp.config.navigation.main.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.newsapp.app.resources.bookmarks.presentation.BookmarkScreenData
import com.example.newsapp.app.resources.home.presentation.HomeScreenData
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

sealed class Screen(
    val title: String,
    val identifier: Any,
    val icon: ImageVector
) {
    data object Home : Screen(
        title = "Home",
        identifier = HomeScreenData,
        icon = Icons.Default.Home
    )

    data object Bookmark : Screen(
        title = "Settings",
        identifier = BookmarkScreenData,
        icon = Icons.Default.Settings
    )
}