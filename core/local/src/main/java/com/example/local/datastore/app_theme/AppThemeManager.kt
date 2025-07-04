package com.example.local.datastore.app_theme

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppThemeManager(
    private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = "libria_now_theme_prefs")

    // Keys
    companion object {
        private val THEME_KEY = stringPreferencesKey("theme")
        private val COLOR_SYSTEM_KEY = stringPreferencesKey("color_system")
    }

    val theme: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[THEME_KEY] }

    suspend fun saveTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }

    val colorSystem: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[COLOR_SYSTEM_KEY] }

    suspend fun saveColorSystem(colorSystem: String) {
        context.dataStore.edit { preferences ->
            preferences[COLOR_SYSTEM_KEY] = colorSystem
        }
    }
}