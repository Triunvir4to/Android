package com.example.newsapp.app.news.domain

import com.example.newsapp.api.ApiCaller
import com.example.newsapp.api.ApiImplementation
import com.example.newsapp.api.ApiResponse
import com.example.newsapp.app.news.data.response.NewsResponse
import kotlin.coroutines.CoroutineContext


@ApiImplementation(newBaseUrl = "https://api.worldnewsapi.com/")
class NewsApiCaller : ApiCaller() {
    private var _defaultLanguage = "pt"
    private var defaultText = ""
    private val newsApiKey = envReader
        .loadProperties()
        .getProperty("news_api_token")

    val defaultLanguage: String
        get() = _defaultLanguage

    /**
     * Sets the default language for news queries.
     * This setting will apply to all subsequent calls to `getNews` unless overridden in the method call.
     * @param language The default language to be set.
     */
    fun setDefaultLanguage(language: String) {
        _defaultLanguage = language
    }

    /**
     * Sets the default text for news queries.
     * This setting will apply to all subsequent calls to `getNews` unless overridden in the method call.
     * @param text The default text to be set.
     */
    fun setDefaultText(text: String) {
        defaultText = text
    }

    /**
     * Fetches news articles filtered by country from a specific endpoint.
     * Utilizes a generic API calling mechanism provided by the `callApi` method. This function
     * allows customization of the news search through parameters, with modifiable default values for language
     * and text through provided setters.
     *
     * ### Usage:
     * This function should be called with a valid country code and API key. By default, the search
     * is conducted in Portuguese (Brazil) and without specific text. It returns a structured
     * response wrapped inside an [ApiResponse] containing either the fetched [NewsResponse] or
     * an error in case of failure.
     *
     * ### Example:
     * ```kotlin
     * val apiCaller = NewsApiCaller()
     * apiCaller.setDefaultLanguage("en")
     * apiCaller.setDefaultText("politics")
     * val newsResponse = apiCaller.getNews(context = Dispatchers.IO)
     * ```
     *
     * @param country The country code to filter news articles. Example: "us" for the United States.
     * @param language The language of the news articles, defaulted to "pt-br" (Portuguese, Brazil).
     * @param text The search text to filter the news articles, defaulted to an empty string.
     * @param apiKey The API key used for authenticating the request. Ensure this is kept secure.
     * @param context The coroutine context in which the network call will be executed. Typically,
     *        this should be a background context like `Dispatchers.IO` to offload the IO work from the main thread.
     * @return [ApiResponse] encapsulating the result of the network call. This can either be a successful
     *         [NewsResponse] instance or an error state.
     */
    suspend fun getNews(
        country: String? = null,
        language: String = defaultLanguage,
        text: String = defaultText,
        apiKey: String = newsApiKey,
        context: CoroutineContext
    ): ApiResponse<NewsResponse> {
        return callApi(
            context = context
        ) {
            apiService.get<NewsResponse>(
                endpoint = "search-news",
                queryParams = mapOf(
                    "country" to (country ?: ""),
                    "api-key" to apiKey,
                    "language" to language,
                    "text" to text
                )
            )
        }
    }
}

