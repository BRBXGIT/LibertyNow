package com.example.common.auth

sealed class AuthIntent {
    data object GetSessionToken: AuthIntent()
    data class UpdateAuthState(val state: AuthState): AuthIntent()
}