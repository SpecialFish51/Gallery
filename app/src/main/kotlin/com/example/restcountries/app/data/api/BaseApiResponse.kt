package com.example.pixabaytt.app.data.api

import com.example.pixabaytt.app.data.api.HttpStatus.NOT_FOUND
import retrofit2.Response

abstract class BaseApiResponse {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        return try {
            val response = apiCall()
            when {
                response.isSuccessful ->
                    if (response.body() == emptyList<T>()) {
                        NetworkResult.Empty()
                    } else {
                        NetworkResult.Success(response.body()!!)
                    }
                response.code() == NOT_FOUND -> NetworkResult.NotFound(response.message())
                else -> error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            error(e.message ?: e.toString())
        }
    }


    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")
}

object HttpStatus {
    const val NOT_FOUND = 404
}