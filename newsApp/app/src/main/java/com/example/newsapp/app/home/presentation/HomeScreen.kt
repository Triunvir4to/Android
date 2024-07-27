package com.example.newsapp.app.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.api.ApiResponse

@Composable
private fun Loading() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text(text = "Loading...")
    }
}

@Composable
private fun Fail(error: ApiResponse.Error) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Failed")
        Text(text = error.message)
    }
}

@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState = homeViewModel.state.collectAsState()

    when (val state = uiState.value) {
        is ApiResponse.Loading -> {
            Loading()
        }

        is ApiResponse.Fail -> {
            val error = state.error
            Fail(error = error)
        }

        is ApiResponse.Success -> {

        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}