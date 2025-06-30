package com.example.local.datastore.auth

sealed class LoggingState {
    data object Loading: LoggingState()
    data object LoggedIn: LoggingState()
    data object LoggedOut: LoggingState()
}