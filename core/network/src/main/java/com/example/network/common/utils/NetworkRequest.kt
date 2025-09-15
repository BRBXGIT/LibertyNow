package com.example.network.common.utils

import retrofit2.Response

object NetworkRequest {

    suspend fun <T> exec(
        call: suspend () -> Response<T>
    ): NetworkResponse<T> {
        return try {
            val response = call()

            if (response.isSuccessful) {
                NetworkResponse(
                    response = response.body(),
                    error = NetworkErrors.SUCCESS
                )
            } else {
                val error = processNetworkErrors(response.code())
                NetworkResponse(
                    error = error,
                    label = processNetworkErrorsForUi(error)
                )
            }
        } catch (e: Exception) {
            val error = processNetworkExceptions(e)
            NetworkResponse(
                error = error,
                label = processNetworkErrorsForUi(error)
            )
        }
    }
}
