package com.example.common.auth

import com.example.local.datastore.auth.AuthState

data class AuthState(
    val isLogged: AuthState = AuthState.Loading,
    val sessionToken: String? = null
)
