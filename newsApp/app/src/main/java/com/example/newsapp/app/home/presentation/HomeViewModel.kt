package com.example.newsapp.app.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.app.news.data.model.News
import com.example.newsapp.app.news.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel(

) {
    private val tag = "HomeViewModel"

    init {
        viewModelScope.launch {
            val result = getNews()
            result.forEach {
                Log.d(tag, it.title)
            }
        }
    }

    private suspend fun getNews(): List<News> {
        return getNewsUseCase.invoke(context = Dispatchers.Main)
    }
}