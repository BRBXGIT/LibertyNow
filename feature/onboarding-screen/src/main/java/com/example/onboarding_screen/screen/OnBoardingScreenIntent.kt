package com.example.onboarding_screen.screen

sealed interface OnBoardingScreenIntent {
    data class SaveIsOnBoardingCompleted(val isCompleted: Boolean): OnBoardingScreenIntent
}