package com.example.newsapp.api

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
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
     * Factory for constructing ApiResponse instances from [HttpResponse]. Utilizes inline and
     * reified type parameters to dynamically access and manipulate the generic type [T] at runtime,
     * thus allowing for direct conversion of [HttpResponse] to a domain-specific [Response] or [Error].
     *
     * This design enables efficient and type-safe transformation of raw HTTP responses into structured,
     * application-specific objects, streamlining the handling of network communication results.
     */
    object Factory {

        /**
         * Transforms an [HttpResponse] into a [Response] object, encapsulating the HTTP status,
         * deserialized body of type [T], and headers. The function is suspendable to accommodate
         * potentially blocking IO operations like body deserialization without freezing the main thread.
         *
         * @param response The [HttpResponse] to be transformed.
         * @return A [Response] instance containing the HTTP status, deserialized body, and headers.
         */
        suspend inline fun <reified T> response(response: HttpResponse): Response<T> {
            return Response(response.status, response.body(), response.headers)
        }

        /**
         * Creates an [Error] instance from an [HttpResponse] and an optional error message.
         * This method allows for the encapsulation of error details received from the network
         * into a structured format, facilitating error handling in the application.
         *
         * @param response The [HttpResponse] associated with the error.
         * @param message An optional message providing additional details about the error.
         * @return An [Error] object containing the HTTP status, error message, and headers.
         */
        fun error(response: HttpResponse?, message: String?): Error {
            return Error(response?.status, message, response?.headers)
        }
    }

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