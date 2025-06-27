package com.example.local.datastore.onboarding

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OnBoardingManager(
    private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = "libria_now_on_boarding_prefs")

    // Keys
    companion object {
        private val IS_ON_BOARDING_COMPLETED_KEY = booleanPreferencesKey("is_on_boarding_completed")
    }

    val isOnboardingCompletedFlow: Flow<Boolean?> = context.dataStore.data
        .map { preferences -> preferences[IS_ON_BOARDING_COMPLETED_KEY] }

    suspend fun saveIsOnBoardingCompleted(isCompleted: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_ON_BOARDING_COMPLETED_KEY] = isCompleted
        }
    }

    suspend fun clearIsOnBoardingCompleted() {
        context.dataStore.edit { preferences ->
            preferences.remove(IS_ON_BOARDING_COMPLETED_KEY)
        }
    }
}