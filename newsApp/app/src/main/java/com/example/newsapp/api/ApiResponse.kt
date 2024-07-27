package com.example.newsapp.api

import io.ktor.http.Headers
import io.ktor.http.HttpStatusCode

/**
 * Represents the outcome of an API request in a sealed hierarchy, enabling type-safe handling of
 * both successful and failed responses. This abstraction facilitates operations on API responses
 * by categorizing them into success and error outcomes, each encapsulating relevant data.
 *
 * @param T The type of data expected in a successful API response.
 */
sealed class ApiResponse<out T> {

    /**
     * Represents the successful outcome of an API call, containing the status code, response body, and headers.
     *
     * @param T The type of the response body.
     * @property status The HTTP status code of the response.
     * @property body The deserialized response body.
     * @property headers The HTTP headers associated with the response.
     */
    data class Response<T>(val status: HttpStatusCode, val body: T, val headers: Headers)

    /**
     * Represents an error outcome from an API call, encapsulating the status code, error message, and headers.
     *
     * @property status The HTTP status code of the error response.
     * @property message An optional message providing details about the error.
     * @property headers The HTTP headers associated with the error response.
     */
    data class Error(val status: HttpStatusCode?, val message: String?, val headers: Headers?)


    /**
     * Represents a loading state in the application's response handling mechanism.
     * This object is used to indicate that a request is currently in process and
     * the final data or result is not yet available.
     *
     * Inherits from [ApiResponse] with a type parameter [Nothing] to signify
     * that no data is expected to be held by this object when in the loading state.
     *
     * Usage:
     * This object can be used in UI components to trigger loading indicators like
     * spinners or progress bars while data is being fetched or processed.
     *
     * Example:
     * ```
     * fun fetchData() {
     *     viewModel.stateLiveData.postValue(Loading())
     * }
     * ```
     */
    data object Loading : ApiResponse<Nothing>()

    /**
     * Encapsulates a successful API response, holding the response data.
     *
     * @param T The type of the successful response data.
     * @property response The detailed response data including status, body, and headers.
     */
    data class Success<T>(val response: Response<T>) : ApiResponse<T>()

    /**
     * Represents a failed API response, holding the error details including status, message, and headers.
     *
     * @param T The contextually relevant type, used for type consistency across the ApiResponse hierarchy.
     * @property error The error details encapsulated in an [Error] object.
     */
    data class Fail<T>(val error: Error) : ApiResponse<T>()

}