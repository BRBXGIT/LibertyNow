package com.example.local.datastore.app_theme

sealed interface ThemeState {
    data object Loading: ThemeState
    data object Loaded: ThemeState
}