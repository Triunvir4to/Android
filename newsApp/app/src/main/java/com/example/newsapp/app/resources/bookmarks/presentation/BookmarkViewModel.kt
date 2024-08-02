package com.example.newsapp.app.resources.bookmarks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.app.resources.news.data.database.NewsDatabase
import com.example.newsapp.app.resources.news.data.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    newsDatabase: NewsDatabase
) : ViewModel() {
    private val newsDao = newsDatabase.newsDao()

    fun getBookmarks() = newsDao.getNews()

    fun deleteBookmark(news: News){
        viewModelScope.launch {
            newsDao.deleteNews(news)
        }
    }

}
