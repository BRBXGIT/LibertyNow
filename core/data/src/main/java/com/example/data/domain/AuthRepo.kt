package com.example.data.domain

import com.example.common.functions.NetworkResponse
import com.example.local.datastore.auth.LoggingState
import kotlinx.coroutines.flow.Flow

interface AuthRepo {

    val userSessionToken: Flow<String?>

    val loggingState: Flow<LoggingState>

    suspend fun saveUserSessionToken(token: String)

    suspend fun clearUserSessionToken()

    suspend fun getSessionToken(
        email: String,
        password: String
    ): NetworkResponse

    suspend fun getLikesAmount(sessionToken: String): NetworkResponse

    suspend fun getLikes(
        sessionToken: String,
        itemsPerPage: Int
    ): NetworkResponse
}