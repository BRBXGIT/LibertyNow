package com.example.local.datastore.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthManager(
    private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = "libria_now_auth_prefs")

    // Keys
    companion object {
        private val USER_SESSION_TOKEN_KEY = stringPreferencesKey("user_session_token")
    }

    val userSessionTokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[USER_SESSION_TOKEN_KEY] }

    suspend fun saveUserSessionToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_SESSION_TOKEN_KEY] = token
        }
    }

    suspend fun clearUserSessionToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_SESSION_TOKEN_KEY)
        }
    }
}