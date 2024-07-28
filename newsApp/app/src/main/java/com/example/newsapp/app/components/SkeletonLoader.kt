package com.example.newsapp.app.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SkeletonLoader(
    modifier: Modifier = Modifier,
    shape: Shape,
    colorShimmer: Color = Color.LightGray,
    highlightColor: Color = Color.White,
    animationDuration: Int = 1000,
    delay: Int = 500
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Skeleton Animation")
    val translateAnim = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDuration,
                delayMillis = delay,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = listOf(colorShimmer, highlightColor, colorShimmer),
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = 0f)
    )

    Box(
        modifier = modifier.background(brush = brush, shape = shape)
    )
}

@Preview
@Composable
private fun PreviewSkeletonLoader() {
    SkeletonLoader(
        modifier = Modifier.size(200.dp, 100.dp),
        shape = RoundedCornerShape(12.dp)
    )
}