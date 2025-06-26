package com.example.data.data

import com.example.data.domain.OnBoardingRepo
import com.example.local.datastore.onboarding.OnBoardingManager
import com.example.local.datastore.onboarding.OnBoardingState
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OnBoardingRepoImpl @Inject constructor(
    onBoardingManager: OnBoardingManager
): OnBoardingRepo {

    override val isOnBoardingCompleted = onBoardingManager.isOnboardingCompletedFlow

    override val onBoardingState = isOnBoardingCompleted.map { isCompleted ->
        when (isCompleted) {
            null -> OnBoardingState.Loading
            true -> OnBoardingState.Completed
            false -> OnBoardingState.NotCompleted
        }
    }
}