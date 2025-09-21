package com.example.onboarding_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.data.domain.OnBoardingRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingScreenVM @Inject constructor(
    private val onBoardingRepo: OnBoardingRepo,
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private fun saveIsOnBoardingCompleted() {
        viewModelScope.launch(dispatcherIo) {
            onBoardingRepo.saveIsOnBoardingCompleted(true)
        }
    }

    fun sendIntent(intent: OnBoardingScreenIntent) {
        when (intent) {
            OnBoardingScreenIntent.SaveIsOnBoardingCompleted -> saveIsOnBoardingCompleted()
        }
    }
}