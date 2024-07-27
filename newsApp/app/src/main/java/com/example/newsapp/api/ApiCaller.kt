package com.example.newsapp.api

import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Abstract class for making API calls. It manages the base URL for the API calls and provides
 * a common function to execute API calls.
 *
 * @property apiService An instance of ApiService that is used to make actual API calls.
 */
abstract class ApiCaller {

    @Inject
    lateinit var apiService: ApiService

    /**  Private property to hold the base URL which can be dynamically configured. */
    private var _baseUrl: String = ""

    val baseUrl: String
        get() = _baseUrl

    /**
     * Initializes the ApiCaller. It sets up the initial configuration for the base URL.
     */
    init {
        configureBaseUrl()
    }

    /**
     * Configures the base URL for API calls. It checks for an [ApiImplementation] annotation
     * to see if a new base URL is provided. If provided, it updates the [_baseUrl]; otherwise,
     * it sets the [_baseUrl] to an empty string.
     */
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

    /**
     * Generic function to call an API method defined in [ApiService]. It ensures that the base URL
     * is correctly set before making the call.
     *
     * @param T the type of the return value of the API call.
     * @param context The coroutine context for make async request
     * @param call The lambda expression representing the API call to execute.
     * @return Returns the result of the API call as specified by the type [T].
     */
    protected suspend fun <T> callApi(
        context: CoroutineContext,
        call: suspend ApiService.() -> T
    ): T {
        ensureCorrectBaseUrl()
        return withContext(context) {
            apiService.call()
        }
    }

    /**
     * Ensures that the current base URL of [apiService] matches the configured [_baseUrl].
     * If not, it updates the base URL of [apiService] to match [_baseUrl].
     */
    private fun ensureCorrectBaseUrl() {
        if (apiService.baseUrl != _baseUrl)
            apiService.baseUrl = _baseUrl
    }

}