package com.example.newsapp.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.OkHttpClient

/**
 * Represents a service responsible for making HTTP requests to an API server.
 * This service handles authentication via tokens and manages token refresh automatically
 * if a 401 Unauthorized response is encountered. It supports operations such as GET and POST,
 * and can extend to support other HTTP methods as needed.
 *
 * @param baseOkHttpClient The base OkHttpClient used to make HTTP requests.
 *                          This client should not contain any authentication-specific interceptors
 *                          as this class manages headers dynamically.
 * @param logger Logger instance used to log messages and errors associated with network requests.
 * @param exceptionRecorder A lambda function that is called to record exceptions.
 *                          This could be connected to a logging service or a crash monitoring tool.
 */
class ApiService(
    baseOkHttpClient: OkHttpClient,
    logger: Logger,
    var exceptionRecorder: (Exception) -> Unit
) {
    /** Mutex to control concurrency during token refresh operations. */
    private val refreshTokenMutex: Mutex = Mutex()

    /** Backing field to hold the authentication token. */
    private var _authToken: String? = null

    /**
     * Public property to get or set the authentication token.
     * Setting this property does not affect ongoing requests but will affect subsequent requests.
     */
    private var authToken: String?
        get() = _authToken
        set(value) {
            _authToken = value
        }

    /** Backing field for the base URL of the API. */
    private var _baseUrl: String = ""

    /**
     * Base URL for API requests.
     * Must start with "https://" to ensure secure requests.
     * Throws IllegalArgumentException if a non-HTTPS URL is set.
     */
    var baseUrl: String
        get() = _baseUrl
        set(value) {
            require(value.startsWith("https://")) { "URL must use HTTPS protocol." }
            _baseUrl = value
        }

    /** Flag to prevent multiple concurrent token refresh attempts. */
    private var isTokenRefreshing: Boolean = false

    /** Custom HTTP status code representing an invalid or expired token. */
    val InvalidToken = HttpStatusCode(498, "Token Expired/Invalid")

    /**
     * HttpClient configured with the base OkHttpClient and additional features
     * like logging and content negotiation.
     */
    val client: HttpClient = createHttpClient(baseOkHttpClient, logger)

    /**
     * Creates an instance of HttpClient configured for API communication.
     * This includes setting up loggers, content negotiation, and default request headers.
     *
     * @param okHttpClient Base OkHttpClient for underlying HTTP requests.
     * @param logger Logger for logging HTTP request and response details.
     * @return Configured HttpClient instance.
     */
    private fun createHttpClient(
        okHttpClient: OkHttpClient,
        logger: Logger
    ): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                preconfigured = okHttpClient
            }

            install(ContentNegotiation) {
                json()
            }

            install(Logging) {
                this.logger = logger
                level = LogLevel.HEADERS
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 20000
            }

            defaultRequest {
                header("Accept", ContentType.Application.Json.toString())
                authToken?.let {
                    header("Authorization", "Bearer $it")
                }
            }
        }
    }

    /**
     * Refreshes the authentication token if it is expired or invalid.
     * This function is synchronized to prevent concurrent token refresh attempts.
     */
    suspend fun refreshToken() {
        refreshTokenMutex.withLock {
            if (!isTokenRefreshing) {
                isTokenRefreshing = true
                try {

                } catch (e: Exception) {

                } finally {
                    isTokenRefreshing = false
                }
            }
        }
    }

    /**
     * Handles API errors by logging the error message and displaying a toast message with an error message to the user.
     *
     * @param e The exception representing the API error.
     * @return `null` to indicate that the operation resulted in an error.
     */
    suspend inline fun <reified T> handleApiError(
        e: Exception,
        response: HttpResponse?,
    ): ApiResponse.Fail<T> {
        val errorMessage = when (e) {
            is ClientRequestException -> {
                if (e.response.status.value > InvalidToken.value) {
                    Log.e("ApiService", "Internal Server Error: ${e.response.bodyAsText()}", e)
                    exceptionRecorder(e)
                }
                Log.e("ApiService", "Error in response with body: ${e.response.bodyAsText()}", e)

                e.response.bodyAsText()
            }

            is HttpRequestTimeoutException -> {
                exceptionRecorder(e)
                Log.e("ApiService", "Timeout Exception: ${e.message}", e)
                "Timeout Exception: ${e.message}"
            }

            else -> {
                exceptionRecorder(e)
                Log.e("ApiService", "Unexpected Exception: ${e.message}", e)
                e.message
            }
        }
        return ApiResponse.Fail(ApiResponse.Factory.error(response, errorMessage))
    }

    /**
     * Tratamento padrão de erros
     */
    fun onFail(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(
                context,
                "Ocorreu um erro, tente novamente mais tarde.",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    /**
     * Retries an HTTP request with the provided [requestLogic] by adding the authentication token header
     * to the request and resending it using the configured HTTP client. This function is used to retry
     * a request after a token refresh in case of a 401 Unauthorized response.
     *
     * @param requestLogic The HttpRequestBuilder containing the details of the original request.
     * @return The response body of the retried request as an object of type [T].
     * @throws ClientRequestException if there is an issue with the request.
     * @throws RuntimeException if there is an error while retrying the request.
     * @param <T> The type of the expected response.
     */
    suspend inline fun <reified T> retryRequest(requestLogic: () -> HttpResponse): ApiResponse<T> {
        refreshToken()

        val response: HttpResponse = requestLogic()

        if (response.status.value >= HttpStatusCode.BadRequest.value)
            return ApiResponse.Fail(
                ApiResponse.Factory.error(
                    response,
                    "Error, refresh the token and retry the request, but get: ${response.bodyAsText()}"
                )
            )

        return ApiResponse.Success(ApiResponse.Factory.response(response))
    }


    /**
     * Executes an HTTP request and handles the response.
     *
     * This generic function is designed for making network calls and handling the responses in a standardized manner.
     * It abstracts the boilerplate of executing requests and handling errors, providing a clean interface for network operations.
     *
     * @param T The type of the data model that the response body will be converted to.
     * @param requestLogic A lambda function that executes the HTTP request and returns an [HttpResponse].
     *                     This lambda contains the actual network call logic.
     * @return [ApiResponse] A generic class representing the result of the network call.
     *         It returns [ApiResponse.Success] with the data of type [T] if the request is successful,
     *         or [ApiResponse.Error] in case of any exceptions.
     *
     * @throws Exception Captures any exceptions thrown during the execution of the request logic.
     *
     * Usage:
     * ```
     * return executeRequest {
     *    client.get(baseUrl + endpoint)
     * }
     *
     * ```
     */
    suspend inline fun <reified T> executeRequest(requestLogic: () -> HttpResponse): ApiResponse<T> {
        var response: HttpResponse? = null
        return try {
            response = requestLogic()
            if (response.status.value == InvalidToken.value)
                return retryRequest<T>(requestLogic)
            if (response.status.value >= HttpStatusCode.BadRequest.value)
                throw ClientRequestException(
                    response,
                    "HTTP request failed with status code ${response.status}"
                )

            ApiResponse.Success(ApiResponse.Factory.response(response))
        } catch (e: Exception) {
            handleApiError(e, response)
        }
    }

    /**
     * Sends a GET request to the specified API endpoint and returns the response body as the specified type [T].
     * This function uses reified type parameters to allow for automatic type casting of the response body.
     *
     * Example Usage:
     * ```
     * // Assume MyData is a data class representing the JSON response
     * val apiResponse: ApiResponse<MyData> = apiService.get("your/endpoint/path")
     * when (apiResponse) {
     *     is ApiResponse.Success -> {
     *         // Handle successful response
     *         val data: MyData = apiResponse.data
     *         // Use 'data' as needed
     *     }
     *     is ApiResponse.Error -> {
     *         // Handle error
     *         val errorMessage = apiResponse.message
     *         // Log or display error message
     *     }
     * }
     * ```
     *
     * @param endpoint The API endpoint to request data from.
     * @return ApiResponse<T> The response body as an object of type [T], wrapped in an ApiResponse to handle success or error.
     * @throws Exception If there is an error during the HTTP request.
     */
    suspend inline fun <reified T> get(
        endpoint: String,
        queryParams: Map<String, String> = emptyMap()
    ): ApiResponse<T> {
        return executeRequest<T> {
            client.get(baseUrl + endpoint) {
                contentType(ContentType.Application.Json)
                queryParams.forEach { (key, value) ->
                    parameter(key, value)
                }
            }
        }
    }

    /**
     * Sends a POST request to the specified API endpoint with the provided payload and returns the response body as the specified type [T].
     * This function uses reified type parameters to enable automatic type casting of the response body.
     *
     * Example Usage:
     * ```
     * // Assuming RequestData is a data class that represents the request payload
     * // and ResponseData is a data class that represents the JSON response
     * val requestData = RequestData( /* initialize with appropriate data */ )
     * val apiResponse: ApiResponse<ResponseData> = apiService.post("your/endpoint/path", requestData)
     * when (apiResponse) {
     *     is ApiResponse.Success -> {
     *         // Handle successful response
     *         val responseData: ResponseData = apiResponse.data
     *         // Use 'responseData' as needed
     *     }
     *     is ApiResponse.Error -> {
     *         // Handle error
     *         val errorMessage = apiResponse.message
     *         // Log or display error message
     *     }
     * }
     * ```
     *
     * @param endpoint The API endpoint to send the POST request to.
     * @param payload The payload to include in the POST request body. It should be of a type that can be serialized to JSON.
     * @return ApiResponse<T> The response body as an object of type [T], wrapped in an ApiResponse to handle success or error.
     * @throws Exception If there is an error during the HTTP request.
     */
    suspend inline fun <reified T> post(endpoint: String, payload: Any): ApiResponse<T> {
        return executeRequest<T> {
            client.post(baseUrl + endpoint) {
                contentType(ContentType.Application.Json)
                setBody(payload)
            }
        }
    }

    /**
     * Sends a PUT request to the specified API endpoint with the provided payload and returns the response body as the specified type [T].
     * This function uses reified type parameters for automatic type casting of the response body.
     *
     * Example Usage:
     * ```
     * // Assuming RequestData is a data class for the request payload
     * // and ResponseData is a data class for the JSON response
     * val requestData = RequestData( /* initialize with appropriate data */ )
     * val apiResponse: ApiResponse<ResponseData> = apiService.put("your/endpoint/path", requestData)
     * when (apiResponse) {
     *     is ApiResponse.Success -> {
     *         // Handle successful response
     *         val responseData: ResponseData = apiResponse.data
     *         // Use 'responseData' as needed
     *     }
     *     is ApiResponse.Error -> {
     *         // Handle error
     *         val errorMessage = apiResponse.message
     *         // Log or display error message
     *     }
     * }
     * ```
     *
     * @param endpoint The API endpoint to send the PUT request to.
     * @param payload The payload to include in the PUT request body, which should be serializable to JSON.
     *
     * @return ApiResponse<T> The response body as an object of type [T], wrapped in an ApiResponse to handle success or error.
     * @throws Exception If there is an error during the HTTP request.
     */
    suspend inline fun <reified T> put(endpoint: String, payload: Any): ApiResponse<T> {
        return executeRequest<T> {
            client.put(baseUrl + endpoint) {
                contentType(ContentType.Application.Json)
                setBody(payload)
            }
        }
    }

    /**
     * This function allows for making HTTP PATCH requests to update resources on the server. It serializes the given body to JSON and sends it to the specified endpoint. The response is wrapped in an `ApiResponse` object, which can either be a success or an error response, depending on the outcome of the request.
     *
     * Example usage:
     *
     * ```kotlin
     * data class UserProfile(val name: String, val email: String)
     *
     *
     * // Example function call:
     * suspend fun updateUserProfile(userId: String, profileUpdates: UserProfile) {
     *     val endpoint = "/users/$userId"
     *     val response: ApiResponse<UserProfile> = patch(endpoint, profileUpdates)
     *
     *     when (response) {
     *         is ApiResponse.Success -> println("Profile updated successfully: ${response.data}")
     *         is ApiResponse.Error -> println("Error updating profile: ${response.exception.message}")
     *     }
     * }
     *
     * // Usage:
     * val userId = "12345"
     * val updates = UserProfile(name = "Jane Doe", email = "jane.doe@example.com")
     *
     * // Coroutine scope needed to call suspend functions
     * CoroutineScope(Dispatchers.IO).launch {
     *     updateUserProfile(userId, updates)
     * }
     * ```
     *
     * @param endpoint The endpoint URL to which the PATCH request will be sent, appended to the [baseUrl].
     * @param body The changes to be applied to the resource, serialized as JSON.
     * @return [ApiResponse<T>] wrapping the response. This can be a success or error response.
     * @param <T> The type of the expected response. This allows for automatic deserialization of the response body to the specified type.
     */
    suspend inline fun <reified T> patch(endpoint: String, body: Any): ApiResponse<T> {
        return executeRequest<T> {
            client.patch(baseUrl + endpoint) {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    }

    /**
     * Sends a DELETE request to the specified API endpoint and returns the response body as the specified type [T].
     * This function uses reified type parameters for automatic type casting of the response body.
     *
     * DELETE operations may not always return a complex data structure. In some cases, the response might be a simple confirmation message or even an empty response. It's important to handle these scenarios appropriately in your application logic.
     *
     * Example Usage:
     * ```
     * // Assuming ResponseData is a data class for the JSON response, or use Unit if no response data is expected
     * val apiResponse: ApiResponse<ResponseData> = apiService.delete("your/endpoint/path")
     * when (apiResponse) {
     *     is ApiResponse.Success -> {
     *         // Handle successful response
     *         val responseData: ResponseData = apiResponse.data
     *         // Use 'responseData' as needed, or handle the success case if responseData is Unit
     *     }
     *     is ApiResponse.Error -> {
     *         // Handle error
     *         val errorMessage = apiResponse.message
     *         // Log or display error message
     *     }
     * }
     * ```
     *
     * @param endpoint The API endpoint to send the DELETE request to.
     * @return ApiResponse<T> The response body as an object of type [T], wrapped in an ApiResponse to handle success or error. The type might be Unit or a specific type based on the API's response.
     * @throws Exception If there is an error during the HTTP request.
     */
    suspend inline fun <reified T> delete(endpoint: String): ApiResponse<T> {
        return executeRequest<T> {
            client.delete(baseUrl + endpoint)
        }
    }
}
