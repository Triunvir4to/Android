package com.example.newsapp.app.resources.news.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.newsapp.app.resources.news.data.model.News
import com.example.newsapp.services.loader.state.State

@Composable
fun NewsDetailsScreen(
    navController: NavController,
    news: News
) {
    val newsViewModel: NewsDetailsViewModel = hiltViewModel()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            model = news.image,
            contentDescription = "news ${news.title} Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.Crop
        )

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val (backBtn, topSpace, summary, newContent) = createRefs()
            Spacer(
                modifier = Modifier
                    .height(350.dp)
                    .constrainAs(topSpace) {
                        top.linkTo(parent.top)
                    }
            )

            Image(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back Button",
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
                    .constrainAs(backBtn) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    }
                    .clickable {
                        navController.popBackStack()
                    }
            )

            Box(modifier = Modifier
                .constrainAs(newContent) {
                    top.linkTo(topSpace.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.wrapContent
                }
                .padding(top = 32.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 50.dp, horizontal = 16.dp)) {
                Text(
                    text = news.text,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(modifier = Modifier
                .padding(vertical = 16.dp)
                .width(300.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.onSurface)
                .padding(16.dp)
                .constrainAs(summary) {
                    top.linkTo(topSpace.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(topSpace.bottom)
                }) {
                Text(
                    text = news.publishDate,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.surface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = news.title, fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    lineHeight = 1.sp,
                    text = news.authors?.joinToString(", ") ?: news.author ?: "",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }

        Image(
            imageVector = Icons.Filled.AddCircle,
            contentDescription = "Add to favorite Button",
            modifier = Modifier
                .padding(16.dp)
                .height(48.dp)
                .width(48.dp)
                .align(Alignment.BottomEnd)
                .clickable {
                    newsViewModel.addNews(news)
                }
        )
    }
    val context = LocalContext.current
    val state = newsViewModel.state.collectAsState()
    LaunchedEffect(state) {
        state.value.let{
            when(it){
                is State.Error -> {
                    Toast.makeText(
                        context,
                        "Ocorreu um erro, tente novamente mais tarde",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is State.Success-> {
                    Toast.makeText(
                        context,
                        "Salvo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {}
            }
        }
    }

}