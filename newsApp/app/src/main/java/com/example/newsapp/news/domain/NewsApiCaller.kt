package com.example.newsapp.news.domain

import com.example.newsapp.api.ApiCaller
import com.example.newsapp.api.ApiImplementation
import com.example.newsapp.api.ApiService


@ApiImplementation(newBaseUrl = "https://api.worldnewsapi.com/")
class NewsApiCaller (
    apiService: ApiService
): ApiCaller(apiService) {

    override fun toString(): String {
        return "Consegui injetar"
    }

}