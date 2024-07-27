package com.example.newsapp.api

abstract class ApiCaller (
    protected val apiService: ApiService
) {
    init{
        configureBaseUrl()
    }

    private fun configureBaseUrl() {
        this::class.annotations.find { it is ApiImplementation }?.let {
            it as ApiImplementation
            if (it.newBaseUrl.isNotEmpty()) {
                apiService.baseUrl = it.newBaseUrl
                return
            }
        }
        apiService.baseUrl = ""
    }

}