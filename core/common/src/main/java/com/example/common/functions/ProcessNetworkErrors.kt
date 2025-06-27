package com.example.common.functions

class NetworkException(
    val error: NetworkError,
    val label: String
): Exception()

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
        NetworkErrors.SUCCESS -> "All is successful"
        NetworkErrors.UNAUTHORIZED -> "Seems you are an unauthorized person"
        NetworkErrors.REQUEST_TIMEOUT -> "Timeout, please refresh"
        NetworkErrors.CONFLICT -> "Seems something conflict"
        NetworkErrors.PAYLOAD_TOO_LARGE -> "Payload too large"
        NetworkErrors.TOO_MANY_REQUESTS -> "Too many requests, give soundcloud a little rest :)"
        NetworkErrors.SERVER_ERROR -> "Server error"
        NetworkErrors.INTERNET -> "Please connect to internet :)"
        else -> "Mystic and unknown error"
    }
}