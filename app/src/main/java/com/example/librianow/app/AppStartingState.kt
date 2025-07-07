package com.example.librianow.app

import com.example.local.datastore.app_theme.ThemeState
import com.example.local.datastore.onboarding.OnBoardingState

data class AppStartingState(
    val onBoardingState: OnBoardingState = OnBoardingState.Loading,
    val themeState: ThemeState = ThemeState.Loading,
    val colorSystemState: ThemeState = ThemeState.Loading,
    val theme: String = "default",
    val colorSystem: String = "default"
)