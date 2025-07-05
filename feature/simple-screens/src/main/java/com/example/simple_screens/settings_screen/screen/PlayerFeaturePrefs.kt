package com.example.simple_screens.settings_screen.screen

data class PlayerFeaturePrefs(
    val videoQuality: Int,
    val autoPlay: Boolean,
    val isCropped: Boolean,
    val showSkipOpeningButton: Boolean
)