package com.example.newsapp.app.news.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.newsapp.app.news.data.model.News
import com.example.newsapp.app.theme.Shapes

@Composable
fun NewsItemComponent(news: News) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.Red.copy(alpha = 0.2f))
            .clip(Shapes.extraLarge)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
        ) {
            Text(text = news.text)
            Text(text = news.publishDate)
        }
    }
}