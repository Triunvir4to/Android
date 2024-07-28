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


/**
 * Creates a skeleton screen loader which is typically used as a placeholder while content is loading.
 * This composable displays an animated shimmer effect over the specified shape.
 *
 * @param modifier A [Modifier] applied to the box that contains the shimmer effect.
 *                 Use this to specify the size, padding, and other layout options.
 * @param shape Defines the shape of the shimmer effect area. Common shapes include [RectangleShape], [RoundedCornerShape], etc.
 * @param colorShimmer The base color of the shimmer effect, typically a shade of gray to represent the unloaded content area.
 * @param highlightColor The color of the shimmer highlight. This color briefly overlays the `colorShimmer` to create a shining effect.
 * @param animationDuration Duration in milliseconds for one cycle of the shimmer animation.
 * @param delay Delay in milliseconds before the animation starts, allowing for staggered animations across multiple loaders.
 *
 * The function uses an infinite transition to animate the linear gradient used for the shimmer. This creates an effect
 * of the shimmer moving across the defined shape. The animation is controlled by `translateAnim`, which shifts the gradient
 * horizontally from 0 to 1000 pixels across the shape's width during each cycle of the animation.
 */
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