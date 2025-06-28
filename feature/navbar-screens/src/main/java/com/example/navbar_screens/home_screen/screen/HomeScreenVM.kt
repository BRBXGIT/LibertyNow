package com.example.navbar_screens.home_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.domain.HomeScreenRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    repository: HomeScreenRepo
): ViewModel() {
    val titlesUpdate = repository.getTitlesUpdates().cachedIn(viewModelScope)

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        HomeScreenState()
    )

    private fun updateScreenState(state: HomeScreenState) {
        _homeScreenState.value = state
    }

    fun sendIntent(intent: HomeScreenIntent) {
        when (intent) {
            is HomeScreenIntent.UpdateScreenState -> updateScreenState(intent.state)
        }
    }
}