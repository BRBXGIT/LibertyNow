package com.example.data.domain

import com.example.local.datastore.app_theme.ThemeState
import kotlinx.coroutines.flow.Flow

interface ThemeRepo {

    val theme: Flow<String?>

    val colorSystem: Flow<String?>

    val themeState: Flow<ThemeState>

    val colorSystemState: Flow<ThemeState>

    suspend fun saveTheme(theme: String)

    suspend fun saveColorSystem(colorSystem: String)
}