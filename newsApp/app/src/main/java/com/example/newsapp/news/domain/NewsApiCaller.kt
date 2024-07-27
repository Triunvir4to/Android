package com.example.newsapp.news.domain

import com.example.newsapp.api.ApiCaller
import com.example.newsapp.api.ApiImplementation


@ApiImplementation(newBaseUrl = "https://api.worldnewsapi.com/")
class NewsApiCaller : ApiCaller() {

}