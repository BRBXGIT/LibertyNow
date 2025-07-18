package com.example.player_screen.screen

import com.example.network.anime_screen.models.anime_details_response.Episode

sealed interface IsPlayingState {
    data object Playing: IsPlayingState
    data object Paused: IsPlayingState
    data object Loading: IsPlayingState
}

data class PlayerScreenState(
    val animeId: Int = 0,

    val currentEpisodeId: Int = 0,
    val episodes: List<Episode> = emptyList(),
    val currentEpisode: Episode = Episode(),

    val isPlaying: IsPlayingState = IsPlayingState.Loading,
    val duration: Long = 0L,
    val currentPosition: Long = 0L,
    val isCropped: Boolean = true,
    val isLocked: Boolean = false,

    val isControllerVisible: Boolean = false,
    val isSelectEpisodeADVisible: Boolean = false,
    val isUserSeeking: Boolean = false,
    val isUnlockButtonVisible: Boolean = false,
    val isSkipOpeningButtonVisible: Boolean = false,
    val isSettingsBSVisible: Boolean = false,
    val isQualityBSVisible: Boolean = false,

    val skipOpeningButtonTimer: Int = 10,
    val timerStarted: Boolean = false,

    val autoPlay: Boolean = true,
    val videoQuality: Int = 1080,
    val showSkipOpeningButton: Boolean = true
)
