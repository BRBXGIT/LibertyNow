package com.example.onboarding_screen.screen

sealed interface OnBoardingScreenIntent {
    data object SaveIsOnBoardingCompleted: OnBoardingScreenIntent
}