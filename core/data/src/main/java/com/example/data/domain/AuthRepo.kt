package com.example.data.domain

import com.example.local.datastore.auth.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthRepo {

    val userSessionToken: Flow<String?>

    val authState: Flow<AuthState>

    suspend fun saveUserSessionToken(token: String)

    suspend fun clearUserSessionToken()
}