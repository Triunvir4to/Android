package com.example.newsapp.news.domain

import com.example.newsapp.api.ApiCaller
import com.example.newsapp.api.ApiImplementation
import com.example.newsapp.api.ApiResponse
import com.example.newsapp.news.data.response.NewsResponse
import kotlin.coroutines.CoroutineContext


@ApiImplementation(newBaseUrl = "https://api.worldnewsapi.com/")
class NewsApiCaller : ApiCaller() {

    /**
     * Fetches news articles filtered by country from a specific endpoint.
     * This function utilizes a generic API calling mechanism provided by the `callApi` method.
     *
     * ### Usage:
     * This function should be called with a valid country code and API key. It returns a structured
     * response wrapped inside an [ApiResponse] containing either the fetched [NewsResponse] or
     * an error in case of failure.
     *
     * ### Example:
     * ```
     * val newsResponse = getNews("us", "your_api_key", Dispatchers.IO)
     * ```
     *
     * @param country The country code to filter news articles. Example: "us" for the United States.
     * @param apiKey The API key used for authenticating the request. Ensure this is kept secure.
     * @param context The coroutine context in which the network call will be executed. Typically, this
     *        should be a background context like `Dispatchers.IO` to offload the IO work from the main thread.
     * @return [ApiResponse] encapsulating the result of the network call. This can either be a successful
     *         [NewsResponse] instance or an error state.
     */
    suspend fun getNews(
        country: String,
        apiKey: String,
        context: CoroutineContext
    ): ApiResponse<NewsResponse> {
        return callApi(
            context = context
        ) {
            apiService.get<NewsResponse>(
                endpoint = "search-news",
                queryParams = mapOf(
                    "country" to country,
                    "api-key" to apiKey
                )
            )
        }
    }
}