package com.example.jetpackcompose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kotlin.math.min

/**
 * Displays a circular progress indicator with customizable text elements.
 *
 * This composable function renders a circular progress indicator that animates both the progress arc and the numerical value.
 * It also displays two text elements inside the circle: a large text showing the current progress with a suffix and a smaller text label.
 * The function allows extensive customization including colors, sizes, and text styles.
 *
 * @param canvasSize The size of the canvas on which the indicator is drawn, defaulting to 300.dp.
 * @param indicatorValue The current value of the indicator which is animated smoothly from 0 to this value.
 * @param maxIndicatorValue The maximum value the indicator can reach, defaulting to 100.
 * @param backgroundIndicatorColor The color of the indicator's background arc, defaulting to a lightly opaque version of the surface color.
 * @param backGroundIndicatorStrokeWidth The stroke width of the background indicator arc, defaulting to 100f.
 * @param foregroundIndicatorColor The color of the foreground indicator arc, which represents actual progress, defaulting to the primary color of the theme.
 * @param foregroundIndicatorStrokeWidth The stroke width of the foreground indicator arc, defaulting to 100f.
 * @param bigTextFontSize The font size for the larger text element showing the numerical value, derived from the theme's headline medium size.
 * @param bigTextColor The color of the larger text, defaulting to the on-surface color of the theme.
 * @param bigTextSuffix A string suffix to append to the big text.
 * @param smallText The text for the smaller label, defaulting to "remaining".
 * @param smallTextFontSize The font size for the smaller text label, derived from the theme's headline small size.
 * @param smallTextColor The color of the smaller text label, defaulting to a lightly opaque version of the on-surface color.
 */
@Composable
fun ProgressIndicator(
    canvasSize: Dp = 300.dp,
    indicatorValue: Int = 0,
    maxIndicatorValue: Int = 100,
    backgroundIndicatorColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    backGroundIndicatorStrokeWidth: Float = 100f,
    foregroundIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    foregroundIndicatorStrokeWidth: Float = 100f,
    bigTextFontSize: TextUnit = MaterialTheme.typography.headlineMedium.fontSize,
    bigTextColor: Color = MaterialTheme.colorScheme.onSurface,
    bigTextSuffix: String,
    smallText: String = "remaining",
    smallTextFontSize: TextUnit = MaterialTheme.typography.headlineSmall.fontSize,
    smallTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
) {
    val animatedIndicatorValue = remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = indicatorValue) {
        animatedIndicatorValue.value = indicatorValue.toFloat()
    }

    val percentage =
        min((animatedIndicatorValue.value / maxIndicatorValue) * 100, 100f)
    val sweepAngle = animateFloatAsState(
        targetValue = (2.4 * percentage).toFloat(),
        animationSpec = tween(
            durationMillis = 1000
        ),
        label = "Indicator Animation"
    )

    val receivedValue = animateIntAsState(
        targetValue = indicatorValue,
        animationSpec = tween(
            durationMillis = 1000
        )
    )

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
                foregroundIndicator(
                    sweepAngle = sweepAngle.value,
                    componentSize = indicatorSize,
                    indicatorColor = foregroundIndicatorColor,
                    indicatorStrokeWidth = foregroundIndicatorStrokeWidth,
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmbedElements(
            bigText = receivedValue.value,
            bigTextFontSize = bigTextFontSize,
            bigTextColor = bigTextColor,
            bigTextSuffix = bigTextSuffix,
            smallText = smallText,
            smallTextColor = smallTextColor,
            smallTextFontSize = smallTextFontSize
        )
    }
}

/**
 * Composes two text elements, one large and one small, with distinct styling options.
 *
 * This function creates a composable UI element that embeds two Text components - one displaying a large number with a suffix,
 * and another displaying a smaller, simpler text. Both texts are centered and styled independently according to the parameters.
 * The large text includes a suffix, of which only the first two characters are used, and is displayed in a bold font weight.
 *
 * @param bigText The integer value to display as the big text.
 * @param bigTextFontSize The font size for the big text.
 * @param bigTextColor The color of the big text.
 * @param bigTextSuffix A string suffix to append to the big text, only the first two characters of which are displayed.
 * @param smallText The string to display as the small text.
 * @param smallTextColor The color of the small text.
 * @param smallTextFontSize The font size for the small text.
 */
@Composable
private fun EmbedElements(
    bigText: Int,
    bigTextFontSize: TextUnit,
    bigTextColor: Color,
    bigTextSuffix: String,
    smallText: String,
    smallTextColor: Color,
    smallTextFontSize: TextUnit
) {
    Text(
        text = smallText,
        color = smallTextColor,
        fontSize = smallTextFontSize,
        textAlign = TextAlign.Center
    )

    Text(
        text = "$bigText ${bigTextSuffix.take(2)}",
        color = bigTextColor,
        fontSize = bigTextFontSize,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}

/**
 * Draws a rounded background indicator arc on the canvas.
 *
 * This function draws an arc representing the foreground indicator of a circular component,
 * such as a progress bar or a meter. The arc starts from a 150-degree angle and sweeps across
 * based on the specified `sweepAngle`. The arc is centered within the provided component size
 * and is styled according to the specified color and stroke width.
 *
 * @param sweepAngle The angle, in degrees, to sweep the arc (e.g., how much of the circle to cover).
 * @param componentSize The size of the component within which the arc is drawn. It determines the overall size of the arc.
 * @param indicatorColor The color to use for the indicator arc.
 * @param indicatorStrokeWidth The thickness of the indicator arc's line.
 */
private fun DrawScope.backgroundIndicator(
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
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2,
            y = (size.height - componentSize.height) / 2
        )
    )
}

/**
 * Draws a rounded foreground indicator arc on the canvas.
 *
 * This function draws an arc representing the foreground indicator of a circular component,
 * such as a progress bar or a meter. The arc starts from a 150-degree angle and sweeps across
 * based on the specified `sweepAngle`. The arc is centered within the provided component size
 * and is styled according to the specified color and stroke width.
 *
 * @param sweepAngle The angle, in degrees, to sweep the arc (e.g., how much of the circle to cover).
 * @param componentSize The size of the component within which the arc is drawn. It determines the overall size of the arc.
 * @param indicatorColor The color to use for the indicator arc.
 * @param indicatorStrokeWidth The thickness of the indicator arc's line.
 */
private fun DrawScope.foregroundIndicator(
    sweepAngle: Float,
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            cap = StrokeCap.Round,
            width = indicatorStrokeWidth
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2,
            y = (size.height - componentSize.height) / 2
        )
    )
}

@Preview
@Composable
private fun ProgressIndicatorPreview() {
    ProgressIndicator(
        bigTextSuffix = "GB"
    )
}