package com.example.jetpackcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.model.Person

@Composable
fun CustomItem(
    person: Person
) {
    Row(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = "${person.age}",
            color = Color.Black,
            fontSize = MaterialTheme.typography.titleSmall.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 0.dp,
                    bottom = 0.dp
                ),
            text = person.firstName,
            color = Color.Black,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Normal
        )
        Text(
            text = person.lastName,
            color = Color.Black,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview
@Composable
private fun CustomItemPreview() {
    CustomItem(person = Person(id = 0, firstName = "John", lastName = "Doe", age = 28))
}