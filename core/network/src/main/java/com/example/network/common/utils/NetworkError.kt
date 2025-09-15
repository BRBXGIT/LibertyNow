package com.example.network.common.utils

interface NetworkError

enum class NetworkErrors: NetworkError {
    SUCCESS,
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    TOO_MANY_REQUESTS,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    INTERNET,
    SERIALIZATION,
    UNKNOWN,
    INCORRECT_EMAIL_OR_PASSWORD
}