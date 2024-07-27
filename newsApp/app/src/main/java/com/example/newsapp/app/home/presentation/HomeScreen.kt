package com.example.newsapp.app.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(){
    val homeViewModel: HomeViewModel = hiltViewModel()
}

@Preview
@Composable
private fun HomeScreenPreview(){
    HomeScreen()
}