package com.example.jetpackcompose

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.ui.theme.CompleteRoundedShape

@Composable
fun SocialLoginButton(
    loadingText: String = "Creating account...",
    icon: Painter,
    iconDescription: String,
    onClick: () -> Unit
) {
    val clicked = remember { mutableStateOf(false) }
    Surface(
        enabled = !clicked.value,
        onClick = {
            clicked.value = !clicked.value
            onClick()
        },
        shape = CompleteRoundedShape,
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .padding(
                    all = 12.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = iconDescription,
                tint = Color.Unspecified
            )

            if (clicked.value) {
                Text(
                    modifier = Modifier
                        .padding(
                            start = 8.dp, end = 8.dp,
                            top = 0.dp, bottom = 0.dp
                        ), text = loadingText
                )
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
@Preview
private fun SocialLoginButtonPreview() {
    SocialLoginButton(
        icon = painterResource(id = R.drawable.ic_google),
        iconDescription = "Google Icon",
        onClick = {
            Log.d("Loging", "Google Login")
        })
}