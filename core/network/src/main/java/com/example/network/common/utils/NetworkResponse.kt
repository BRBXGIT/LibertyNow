package com.example.network.common.utils

data class NetworkResponse<T>(
    val response: T? = null,
    val error: NetworkError? = null,
    val label: String? = null
)
