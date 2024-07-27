package com.example.newsapp.app.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.api.ApiResponse
import com.example.newsapp.app.news.data.model.News
import com.example.newsapp.app.news.data.response.NewsResponse
import com.example.newsapp.app.news.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel(

) {
    private val tag = "HomeViewModel"

    private val _state = MutableStateFlow<ApiResponse<NewsResponse>>(ApiResponse.Loading)
    val state = _state as StateFlow<ApiResponse<NewsResponse>>

    init {
        viewModelScope.launch {
            val result = getNews()
            result.forEach {
                Log.d(tag, it.title)
            }
        }
    }

    private suspend fun getNews(): List<News> {
        _state.tryEmit(ApiResponse.Loading)
        val request = getNewsUseCase.invoke(
            context = Dispatchers.IO
        )

        _state.tryEmit(request)

        return when (request) {
            is ApiResponse.Success -> {
                request.response.body.news
            }

            else -> {
                emptyList()
            }
        }
    }
}