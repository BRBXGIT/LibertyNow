package com.example.navbar_screens.more_screen.screen

sealed interface MoreScreenIntent {
    data class UpdateScreenState(val state: MoreScreenState): MoreScreenIntent
}