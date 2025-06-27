package com.example.data.data

import com.example.data.domain.OnBoardingRepo
import com.example.local.datastore.onboarding.OnBoardingManager
import com.example.local.datastore.onboarding.OnBoardingState
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OnBoardingRepoImpl @Inject constructor(
    private val onBoardingManager: OnBoardingManager
): OnBoardingRepo {

    override val isOnBoardingCompleted = onBoardingManager.isOnboardingCompletedFlow

    override val onBoardingState = isOnBoardingCompleted.map { isCompleted ->
        when (isCompleted) {
            null -> OnBoardingState.NotCompleted
            true -> OnBoardingState.Completed
            false -> OnBoardingState.NotCompleted
        }
    }

    override suspend fun saveIsOnBoardingCompleted(isCompleted: Boolean) {
        onBoardingManager.saveIsOnBoardingCompleted(isCompleted)
    }

    override suspend fun clearIsOnBoardingCompleted() {
        onBoardingManager.clearIsOnBoardingCompleted()
    }
}