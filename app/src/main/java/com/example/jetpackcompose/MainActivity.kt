package com.example.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackcompose.repository.PersonRepository
import com.example.jetpackcompose.ui.theme.JetPackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetPackComposeTheme {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                }
            }
        }
    }
}

@Composable
fun LazyColumnTest(){
    val personRepository = PersonRepository()
    val persons = personRepository.getAllData()
    LazyColumn(
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = persons) { person ->
            CustomItem(person = person)
        }
    }
}

@Composable
fun TestCoilLib() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .height(150.dp)
                .width(150.dp),
            contentAlignment = Alignment.Center
        ) {
            val painter =
                rememberAsyncImagePainter(model = "https://avatars.githubusercontent.com/u/75641510?v=4")
            Image(painter = painter, contentDescription = "Github Image")

            val painterState = painter.state

            if (painterState is AsyncImagePainter.State.Loading)
                CircularProgressIndicator()

        }
    }
}

@Composable
fun ColumnScope.CustomItem(weight: Float, color: Color = MaterialTheme.colorScheme.primary) {
    Surface(
        modifier = Modifier
            .width(200.dp)
            .height(50.dp)
            .weight(weight),
        color = color
    ) {}
}

@Composable
fun BoxTesting() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .background(Color.Green)
            )
            Text(text = "Texting", fontSize = 40.sp)
        }
    }
}

@Composable
fun RowColumnTest() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CustomItem(weight = 3f, MaterialTheme.colorScheme.secondary)
        CustomItem(weight = 1f)
    }
}

@Composable
fun TextTesting() {
    Box(modifier = Modifier.fillMaxSize()) {
        SelectionContainer {
            Column {
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(16.dp)
                )
                DisableSelection {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(16.dp)
                    )
                }

            }
        }
    }
}

@Composable
fun SuperScriptText(
    normalText: String,
    superText: String
) {
    Text(text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = MaterialTheme.typography.titleSmall.fontSize
            )
        ) {
            append(normalText)
        }
        withStyle(
            style = SpanStyle(
                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                fontWeight = FontWeight.Normal,
                baselineShift = BaselineShift.Superscript
            )
        ) {
            append(superText)
        }
    })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetPackComposeTheme {
        SuperScriptText("Rafael", "Alves")
    }
}