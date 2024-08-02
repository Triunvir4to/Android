package com.example.newsapp.app.resources.news.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.app.resources.news.data.model.News

/**
 * Composable function that provides a common background layout for the main content area.
 *
 * This function creates a lazy column layout which fills the maximum available size. It begins
 * with a static text item displaying "Notícias" and then renders additional items as specified
 * by the [renderingItems] lambda function.
 *
 * @param renderingItems A lambda function that takes a LazyListScope and applies to define the content.
 *                       This allows for dynamic and flexible content composition within the lazy column.
 */
@Composable
private fun MainContentBackground(renderingItems: LazyListScope.() -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Text(text = "Notícias")
        }
        renderingItems()
    }
}

/**
 * Composable function to display a loading skeleton for news list items.
 *
 * It calculates the number of skeletons to fill the screen based on the screen height and
 * a predefined skeleton height. This provides a visual placeholder while the news content is loading.
 */
@Composable
fun NewsListComponentLoading() {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val skeletonHeight = 130.dp
    val count = (screenHeight / skeletonHeight).toInt()
    MainContentBackground {
        repeat(count) {
            item {
                NewsSkeletonLoader()
            }
        }
    }
}

/**
 * Composable function to display a list of news items.
 *
 * It uses the [MainContentBackground] to maintain a consistent design and passes the news data
 * to [NewsItemComponent] for rendering individual news articles. When a news item is clicked,
 * it navigates to the detailed view of the article using the provided NavController.
 *
 * @param navController The NavController used for navigating between composables.
 * @param news A list of news items to be displayed.
 */
@Composable
fun NewsListComponent(
    navController: NavController,
    news: List<News>
) {
    MainContentBackground {
        items(news) { article ->
            NewsItemComponent(
                news = article,
                onClick = {
                    navController.navigate(article)
                }
            )
        }
    }
}