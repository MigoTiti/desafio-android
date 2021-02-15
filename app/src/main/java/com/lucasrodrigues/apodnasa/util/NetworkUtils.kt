package com.lucasrodrigues.apodnasa.util

import com.lucasrodrigues.apodnasa.domain.model.Failure
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.UnknownHostException

suspend fun <DTO> requestApi(apiCall: suspend () -> DTO): DTO {
    return try {
        apiCall()
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> throw Failure.NetworkConnection
            is UnknownHostException -> throw Failure.NetworkConnection
            is HttpException -> when (throwable.code()) {
                HttpURLConnection.HTTP_UNAVAILABLE,
                HttpURLConnection.HTTP_INTERNAL_ERROR -> throw Failure.ServerError(
                    throwable.message()
                )
                else -> throw throwable
            }
            else -> throw Failure.UnknownError(throwable.message ?: "")
        }
    }
}