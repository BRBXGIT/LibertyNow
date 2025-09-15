package com.example.data.domain

import com.example.local.datastore.auth.LoggingState
import com.example.network.auth.models.session_token_response.SessionTokenResponse
import com.example.network.common.utils.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepo {

    val userSessionToken: Flow<String?>

    val loggingState: Flow<LoggingState>

    suspend fun saveUserSessionToken(token: String)

    suspend fun clearUserSessionToken()

    suspend fun getSessionToken(
        email: String,
        password: String
    ): NetworkResponse<SessionTokenResponse>
}