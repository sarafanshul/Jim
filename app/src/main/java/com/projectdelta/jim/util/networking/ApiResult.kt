package com.projectdelta.jim.util.networking

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Failure(val statusCode: Int?) : ApiResult<Nothing>()
    data object NetworkError : ApiResult<Nothing>()
    data object Empty : ApiResult<Nothing>()
}
