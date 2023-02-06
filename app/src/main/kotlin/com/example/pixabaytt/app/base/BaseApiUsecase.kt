package com.example.pixabaytt.app.base

import com.google.gson.Gson

abstract class BaseApiUseCase(
    private val gson: Gson
) {

    protected suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Result<T> = safeApiCall(gson, apiCall)


    private suspend fun <T> safeApiCall(gson: Gson, apiCall: suspend () -> T): Result<T> = runCatching {
        apiCall()
    }.matchToDomainErrorIfNeeded(gson)

    private fun <T> Result<T>.matchToDomainErrorIfNeeded(gson: Gson): Result<T> = this.mapError { cause ->
        cause
    }

    private inline fun <T> Result<T>.mapError(transform: (Throwable) -> Throwable): Result<T> {
        this.exceptionOrNull()?.let {
            return transform(it).asFailure()
        }
        return this
    }

    private fun <R> Throwable.asFailure(): Result<R> = Result.failure(this)
}