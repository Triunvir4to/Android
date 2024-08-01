package com.example.newsapp.services.loader.state

sealed class State <out T> {
    data object Loading: State<Nothing>()

    data class Error(val error: String) : State<Nothing> ()

    data class Success<T>(val data: T) : State<T>()
}