package com.example.newsapp.config.navigation.main.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.newsapp.app.resources.bookmarks.presentation.BookmarkScreenData
import com.example.newsapp.app.resources.home.presentation.HomeScreenData
import com.example.newsapp.services.screen.BottomBarScreen

sealed class NavigableScreen(
    title: String,
    identifier: Any,
    icon: ImageVector
) : BottomBarScreen (
    title,
    identifier,
    icon
) {
    data object Home : NavigableScreen(
        title = "Home",
        identifier = HomeScreenData,
        icon = Icons.Default.Home
    )

    data object Bookmark : NavigableScreen(
        title = "Favorites",
        identifier = BookmarkScreenData,
        icon = Icons.Default.Favorite
    )
}