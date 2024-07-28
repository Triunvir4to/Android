package com.example.newsapp.app.news.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsapp.app.components.SkeletonLoader
import com.example.newsapp.app.news.data.model.News
import com.example.newsapp.app.ui.theme.Shapes

/**
 * Displays a single news item as a component in a list or grid.
 * This component shows a news article's image, title, publication date, and author information in a styled card.
 *
 * @param news An instance of [News] which contains data like title, author(s), publish date, and image URL.
 * @param onClick What happens when the user click's it
 *
 * The function constructs a box that serves as a card container with a light red background. It uses:
 * - `AsyncImage` to load and display the news image asynchronously, cropping it to fill the container.
 * - Three `Text` composites to display the title at the top center, the publication date at the bottom end,
 *   and the author's name or names at the bottom start. If `news.author` is null, it concatenates and displays `news.authors`.
 */
@Composable
fun NewsItemComponent(
    news: News,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(top = 4.dp, bottom = 8.dp)
            .fillMaxWidth()
            .height(130.dp)
            .clip(Shapes.extraLarge)
            .background(Color.Red.copy(alpha = 0.2f))
            .clickable { onClick() }
    ) {

        val authorCamp = news.author ?: news.authors?.joinToString(", ") ?: ""
        AsyncImage(
            model = news.image,
            contentDescription = "Article ${news.title}: Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Text(
            text = news.title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        Text(
            text = news.publishDate,
            color = Color.White,
            modifier = Modifier.align(Alignment.BottomEnd)
        )

        Text(
            text = authorCamp,
            color = Color.White,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}

/**
 * Displays a skeleton loader for a news item component.
 * This loader is used as a placeholder while the actual news content is being loaded, mimicking the layout of `NewsItemComponent`.
 *
 * This function configures the `SkeletonLoader` to match the appearance and dimensions of a news item card,
 * utilizing a custom shape and size. It leverages the same modifier settings as `NewsItemComponent` to ensure consistency
 * in appearance between the loading and loaded states.
 */
@Composable
fun NewsSkeletonLoader() {
    SkeletonLoader(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
            .height(130.dp),
        shape = Shapes.extraLarge
    )
}