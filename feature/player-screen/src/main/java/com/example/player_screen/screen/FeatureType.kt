package com.example.player_screen.screen

sealed interface FeatureType {
    data class VideoQuality(val quality: Int): FeatureType
    data object AutoPlay: FeatureType
    data object ShowSkipOpeningButton: FeatureType
}