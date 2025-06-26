package com.example.local.datastore.onboarding

sealed class OnBoardingState {
    data object Loading: OnBoardingState()
    data object Completed: OnBoardingState()
    data object NotCompleted: OnBoardingState()
}