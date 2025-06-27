package com.example.onboarding_screen.screen

sealed class OnBoardingScreenIntent {
    data class SaveIsOnBoardingCompleted(val isCompleted: Boolean): OnBoardingScreenIntent()
}