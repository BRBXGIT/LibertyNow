package com.example.simple_screens.settings_screen.screen

data class SettingsScreenState(
    val videoQuality: Int = 1080,
    val showSkipOpeningButton: Boolean = false,
    val autoPlay: Boolean = false,
    val isCropped: Boolean = false,

    val theme: String = "default",
    val colorSystem: String = "default",

    val isQualityBSVisible: Boolean = false
)
