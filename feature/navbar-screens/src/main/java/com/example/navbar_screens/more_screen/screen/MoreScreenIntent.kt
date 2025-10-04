package com.example.navbar_screens.more_screen.screen

sealed interface MoreScreenIntent {

    data object ChangeIsQuitAdVisible: MoreScreenIntent
}