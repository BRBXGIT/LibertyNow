package com.example.network.common.utils

data class NetworkException(
    val error: NetworkError,
    val label: String
): Exception()
