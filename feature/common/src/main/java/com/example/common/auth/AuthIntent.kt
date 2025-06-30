package com.example.common.auth

sealed interface AuthIntent {
    data object GetSessionToken: AuthIntent
    data class UpdateAuthState(val state: AuthState): AuthIntent
}