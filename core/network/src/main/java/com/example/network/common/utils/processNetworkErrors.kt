package com.example.network.common.utils

import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun processNetworkErrors(statusCode: Int): NetworkError {
    return when (statusCode) {
        in 200..299 -> NetworkErrors.SUCCESS
        401 -> NetworkErrors.UNAUTHORIZED
        408 -> NetworkErrors.REQUEST_TIMEOUT
        409 -> NetworkErrors.CONFLICT
        413 -> NetworkErrors.PAYLOAD_TOO_LARGE
        429 -> NetworkErrors.TOO_MANY_REQUESTS
        in 500..599 -> NetworkErrors.SERVER_ERROR
        else -> NetworkErrors.UNKNOWN
    }
}

fun processNetworkErrorsForUi(error: NetworkError): String {
    return when (error) {
        NetworkErrors.SUCCESS -> "All good"
        NetworkErrors.UNAUTHORIZED -> "Seems you are unauthorized"
        NetworkErrors.REQUEST_TIMEOUT -> "Timeout please retry"
        NetworkErrors.CONFLICT -> "Something conflict"
        NetworkErrors.PAYLOAD_TOO_LARGE -> "The server load is too large"
        NetworkErrors.TOO_MANY_REQUESTS -> "Too many requests, give soundcloud a little rest :)"
        NetworkErrors.SERVER_ERROR -> "Server error"
        NetworkErrors.INTERNET -> "Please connect to network"
        NetworkErrors.SERIALIZATION -> "Serialization problem"
        else -> "Unknown error"
    }
}

fun processNetworkExceptions(e: Exception): NetworkError {
    return when (e) {
        is UnknownHostException -> NetworkErrors.INTERNET
        is SocketException -> NetworkErrors.INTERNET
        is SocketTimeoutException -> NetworkErrors.REQUEST_TIMEOUT
        else -> NetworkErrors.UNKNOWN
    }
}