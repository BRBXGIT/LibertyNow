package com.example.navbar_screens.lists_screen.screen

sealed interface ListsScreenIntent {
    data class UpdateScreenState(val state: ListsScreenState): ListsScreenIntent
}