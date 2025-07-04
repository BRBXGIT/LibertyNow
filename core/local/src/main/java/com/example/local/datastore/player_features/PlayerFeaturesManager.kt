package com.example.local.datastore.player_features

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayerFeaturesManager(
    private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = "libria_now_player_feature_prefs")

    // Keys
    companion object {
        private val VIDEO_QUALITY_KEY = intPreferencesKey("video_quality")
        private val SHOW_SKIP_OPENING_BUTTON_KEY = booleanPreferencesKey("show_skip_opening_button")
        private val AUTOPLAY_KEY = booleanPreferencesKey("autoplay")
        private val IS_CROPPED_KEY = booleanPreferencesKey("is_cropped")
    }

    val videoQualityFlow: Flow<Int?> = context.dataStore.data
        .map { preferences -> preferences[VIDEO_QUALITY_KEY] }

    suspend fun saveVideoQuality(quality: Int) {
        context.dataStore.edit { preferences ->
            preferences[VIDEO_QUALITY_KEY] = quality
        }
    }

    val showSkipOpeningButtonFlow: Flow<Boolean?> = context.dataStore.data
        .map { preferences -> preferences[SHOW_SKIP_OPENING_BUTTON_KEY] }

    suspend fun saveShowOpeningButton(show: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SHOW_SKIP_OPENING_BUTTON_KEY] = show
        }
    }

    val autoPlay: Flow<Boolean?> = context.dataStore.data
        .map { preferences -> preferences[AUTOPLAY_KEY] }

    suspend fun saveAutoplay(autoplay: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[AUTOPLAY_KEY] = autoplay
        }
    }

    val isCropped: Flow<Boolean?> = context.dataStore.data
        .map { preferences -> preferences[IS_CROPPED_KEY] }

    suspend fun saveIsCropped(isCropped: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_CROPPED_KEY] = isCropped
        }
    }
}