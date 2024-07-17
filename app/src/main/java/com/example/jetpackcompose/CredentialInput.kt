package com.example.jetpackcompose

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CredentialInput(
    placeHolder: String,
    icons: Array<Painter>,
    iconDescription: String,
    isPassword: Boolean = false,
    keyBoardOptions: KeyboardOptions = KeyboardOptions()
) {
    val field = rememberSaveable { mutableStateOf("") }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val iconIdx = if (passwordVisible.value && icons.size > 1) 0 else 1

    OutlinedTextField(
        value = field.value,
        onValueChange = {
            field.value = it
        },
        placeholder = { Text(text = placeHolder) },
        label = { Text(text = placeHolder) },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        painter = icons[iconIdx],
                        contentDescription = iconDescription,
                        tint = if (passwordVisible.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                Icon(painter = icons[iconIdx], contentDescription = iconDescription)
            }
        },
        keyboardOptions = keyBoardOptions,
        visualTransformation = if (isPassword && passwordVisible.value) VisualTransformation.None
        else PasswordVisualTransformation()
    )
}

@Preview
@Composable
private fun CredentialInputPreview() {
    MaterialTheme {
        CredentialInput(
            placeHolder = "Password",
            icons = arrayOf(
                painterResource(id = R.drawable.ic_visibility_off),
                painterResource(id = R.drawable.ic_visibility)
            ),
            iconDescription = "Toggle password visibility",
            isPassword = true
        )
    }
}
