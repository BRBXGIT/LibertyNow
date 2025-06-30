package com.example.player_screen.screen

interface PlayerScreenIntent {
    data class UpdateScreenState(val state: PlayerScreenState): PlayerScreenIntent

    data object SetMedia: PlayerScreenIntent
}