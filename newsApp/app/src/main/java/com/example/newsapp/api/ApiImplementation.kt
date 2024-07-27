package com.example.newsapp.api

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiImplementation(
    val newBaseUrl: String = ""
)

annotation class AutoInjectApiCaller