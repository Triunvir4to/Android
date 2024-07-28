package com.example.newsapp.app.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.services.api.utils.ApiResponse
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
        getNews()
    }

    fun getNews() {
        viewModelScope.launch {
            _state.tryEmit(ApiResponse.Loading)
            val request = getNewsUseCase.invoke(
                context = Dispatchers.IO
            )

            _state.tryEmit(request)
        }
    }
}