package com.example.jetpackcompose

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.ui.theme.Shapes

/**
 * A custom composable function that creates an expandable card, allowing content to be shown or hidden with an animation.
 * The card includes a title and a body, with the body text expandable on user interaction.
 *
 * @param title The text to be displayed as the title of the card.
 * @param titleFontSize The font size for the title text. Defaults to the small title size from the material theme.
 * @param titleFontWeight The font weight for the title text. Defaults to Bold.
 * @param bodyText The text content that is expandable/collapsible when the card is interacted with.
 * @param bodyFontSize The font size for the body text. Defaults to the medium body size from the material theme.
 * @param bodyFontWeight The font weight for the body text. Defaults to Normal.
 * @param bodyMaxLines The maximum lines the body text is allowed to wrap. Defaults to 4.
 * @param shape The shape of the card, affecting corner styling. Defaults to medium rounded corners.
 * @param padding The padding inside the card, affecting the spacing around the content. Defaults to 12dp.
 *
 * Example Usage:
 * ```
 * ExpandableCard(
 *     title = "Expandable Card",
 *     bodyText = "This is the detailed description that can be expanded or collapsed."
 * )
 * ```
 */
@Composable
fun ExpandableCard(
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.titleSmall.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    bodyText: String,
    bodyFontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    bodyFontWeight: FontWeight = FontWeight.Normal,
    bodyMaxLines: Int = 4,
    shape: CornerBasedShape = Shapes.medium,
    padding: Dp = 12.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary
    ) {
    val expandableState = remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandableState.value) 180f else 0f, label = "Card expanding animation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = shape,

        onClick = {
            expandableState.value = !expandableState.value
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor
                )
                IconButton(
                    modifier = Modifier
                        .alpha(0.5f)
                        .rotate(rotationState),
                    onClick = { expandableState.value = !expandableState.value },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandableState.value) {
                Text(
                    text = bodyText,
                    fontSize = bodyFontSize,
                    fontWeight = bodyFontWeight,
                    maxLines = bodyMaxLines,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExpandableCardPreview() {
    ExpandableCard(title = "Teste", bodyText = "testando meu componente")
}