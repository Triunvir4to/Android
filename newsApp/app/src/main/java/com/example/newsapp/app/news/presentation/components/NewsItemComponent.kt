package com.example.newsapp.app.news.presentation.components

import androidx.compose.foundation.background
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
import com.example.newsapp.app.news.data.model.News
import com.example.newsapp.app.theme.Shapes

@Composable
fun NewsItemComponent(news: News) {
    Box(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
            .height(130.dp)
            .clip(Shapes.extraLarge)
            .background(Color.Red.copy(alpha = 0.2f))
    ) {

        val authorCamp = news.author ?: news.authors?.joinToString(", ")!!
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