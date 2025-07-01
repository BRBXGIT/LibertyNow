package com.example.player_screen.screen

import com.example.network.anime_screen.models.anime_response.X1

sealed interface IsPlayingState {
    data object Playing: IsPlayingState
    data object Paused: IsPlayingState
    data object Loading: IsPlayingState
}

data class PlayerScreenState(
    val currentAnimeId: Int = 0,
    val host: String = "",
    val links: List<X1> = emptyList(),
    val currentLink: X1 = X1(),

    val isPlaying: IsPlayingState = IsPlayingState.Loading,

    val isControllerVisible: Boolean = false
)
