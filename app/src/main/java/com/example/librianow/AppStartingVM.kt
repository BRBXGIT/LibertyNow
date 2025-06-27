package com.example.librianow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.domain.OnBoardingRepo
import com.example.local.datastore.onboarding.OnBoardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppStartingVM @Inject constructor(
    onBoardingRepo: OnBoardingRepo
): ViewModel() {
    val onBoardingState = onBoardingRepo.onBoardingState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        OnBoardingState.Loading
    )
}