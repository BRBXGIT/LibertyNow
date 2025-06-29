package com.example.local.datastore.auth

sealed class AuthState {
    data object Loading: AuthState()
    data object LoggedIn: AuthState()
    data object LoggedOut: AuthState()
}