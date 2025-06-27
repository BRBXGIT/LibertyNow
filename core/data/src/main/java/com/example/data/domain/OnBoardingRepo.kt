package com.example.data.domain

import com.example.local.datastore.onboarding.OnBoardingState
import kotlinx.coroutines.flow.Flow

interface OnBoardingRepo {

    val isOnBoardingCompleted: Flow<Boolean?>

    val onBoardingState: Flow<OnBoardingState>

    suspend fun saveIsOnBoardingCompleted(isCompleted: Boolean)

    suspend fun clearIsOnBoardingCompleted()
}