package com.example.jetpackcompose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ProgressIndicator(
    canvasSize: Dp = 300.dp,
    indicatorValue: Int = 0,
    maxIndicatorValue: Int = 100,
    backgroundIndicatorColor: Color = MaterialTheme.colorScheme.onSurface,
    backGroundIndicatorStrokeWidth: Float = 100f
) {
    Column(
        modifier = Modifier
            .size(canvasSize)
            .drawBehind {
                val indicatorSize = size / 1.25f
                backgroundIndicator(
                    componentSize = indicatorSize,
                    indicatorColor = backgroundIndicatorColor,
                    indicatorStrokeWidth = backGroundIndicatorStrokeWidth
                )
            }
    ) {

    }
}

fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = 240f,
        useCenter = false,
        style = Stroke(
            cap = StrokeCap.Round,
            width = indicatorStrokeWidth
        )
    )
}

@Preview
@Composable
private fun ProgressIndicatorPreview() {
    ProgressIndicator()
}