package com.example.player_screen.screen

interface PlayerScreenIntent {
    data class UpdateScreenState(val state: PlayerScreenState): PlayerScreenIntent
    data object UpdateIsControllerVisible: PlayerScreenIntent

    data object PreparePlayer: PlayerScreenIntent
    data object ReleasePlayer: PlayerScreenIntent
    data object PausePlayer: PlayerScreenIntent
    data class SkipEpisode(val forward: Boolean): PlayerScreenIntent
    data class SetEpisode(val episodeId: Int): PlayerScreenIntent
    data class SeekEpisode(
        val seekTo: Long,
        val quickSeek: Boolean = false
    ): PlayerScreenIntent
}