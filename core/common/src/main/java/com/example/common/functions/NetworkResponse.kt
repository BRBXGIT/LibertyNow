package com.example.common.functions

data class NetworkResponse(
    val response: Any? = null,
    val error: NetworkError? = null,
    val label: String? = null
)
