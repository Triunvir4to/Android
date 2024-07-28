package com.example.newsapp.services.api.config

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiImplementation(
    val newBaseUrl: String = ""
)