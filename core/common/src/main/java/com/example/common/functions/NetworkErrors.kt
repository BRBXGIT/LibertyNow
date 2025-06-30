package com.example.common.functions

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
    INCORRECT_PASSWORD,
    INCORRECT_EMAIL,
    UNKNOWN
}