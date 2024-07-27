package com.example.newsapp.api

abstract class ApiCaller (
    protected val apiService: ApiService
) {
    private var _baseUrl = ""

    init{
        configureBaseUrl()
    }

    private fun configureBaseUrl() {
        this::class.annotations.find { it is ApiImplementation }?.let {
            it as ApiImplementation
            if (it.newBaseUrl.isNotEmpty()) {
                _baseUrl = it.newBaseUrl
                return
            }
        }
        _baseUrl = ""
    }

    protected fun <T> callApi(call: ApiService.() -> T): T {
        ensureCorrectBaseUrl()
        return apiService.call()
    }

    private fun ensureCorrectBaseUrl() {
        if (apiService.baseUrl != _baseUrl)
            apiService.baseUrl = _baseUrl
    }

}