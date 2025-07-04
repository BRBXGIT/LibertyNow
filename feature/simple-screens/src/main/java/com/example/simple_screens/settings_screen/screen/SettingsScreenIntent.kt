package com.example.simple_screens.settings_screen.screen

import com.example.simple_screens.settings_screen.sections.PlayerSettingsItemType

sealed interface SettingsScreenIntent {
    data class SetTheme(val theme: String): SettingsScreenIntent
    data class SetColorSystem(val colorSystem: String): SettingsScreenIntent

    data class SetPlayerFeature(
        val quality: Int? = null,
        val type: PlayerSettingsItemType
    ): SettingsScreenIntent

    data class UpdateScreenState(val state: SettingsScreenState): SettingsScreenIntent
}