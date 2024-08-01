package com.example.newsapp.app.news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.app.news.data.dao.NewsDao
import com.example.newsapp.app.news.data.database.NewsDatabase
import com.example.newsapp.app.news.data.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    database: NewsDatabase
) : ViewModel() {

    private val newsDao: NewsDao = database.newsDao()

    fun addNews(news: News){
        viewModelScope.launch {
            newsDao.addNews(news)
        }
    }

}