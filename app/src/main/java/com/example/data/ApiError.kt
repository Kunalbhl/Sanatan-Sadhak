package com.example.data

sealed class ApiError {
    object NetworkUnavailable : ApiError()
    object MissingConfiguration : ApiError()
    data class Unauthorized(val message: String = "Unauthorized request") : ApiError()
    data class Forbidden(val message: String = "API access forbidden or quota exhausted") : ApiError()
    data class RateLimited(val retryAfterSeconds: Long? = null) : ApiError()
    data class ServerError(val code: Int, val message: String = "Server error") : ApiError()
    data class InvalidResponse(val message: String = "Malformed response") : ApiError()
    data class OfflineCacheAvailable(val sourceName: String) : ApiError()
    object OfflineCacheUnavailable : ApiError()

    fun toUserFriendlyMessage(): String = when (this) {
        is NetworkUnavailable -> "Network unavailable. Displaying offline data."
        is MissingConfiguration -> "Online source unconfigured; using offline/cached data."
        is Unauthorized -> "Service authentication required. Using offline source."
        is Forbidden -> "Online source quota or access restricted; using offline source."
        is RateLimited -> "Service rate limit reached; using offline/cached data."
        is ServerError -> "Online service temporarily unavailable ($code). Using offline data."
        is InvalidResponse -> "Received invalid data from online service. Using local data."
        is OfflineCacheAvailable -> "Using cached $sourceName."
        is OfflineCacheUnavailable -> "No offline data available."
    }
}

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T, val isAuthoritative: Boolean = true) : ApiResult<T>()
    data class Error<out T>(val error: ApiError, val cachedFallbackData: T? = null) : ApiResult<T>()
}
