package com.example.navbar_screens.home_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.domain.HomeScreenRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    repository: HomeScreenRepo
): ViewModel() {
    val titlesUpdates = repository.getTitlesUpdates().cachedIn(viewModelScope)

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        HomeScreenState()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val titlesByQuery = _homeScreenState
        .map { it.query }
        .filterNotNull()
        .distinctUntilChanged()
        .flatMapLatest { query ->
            repository.getTitlesByQuery(query).cachedIn(viewModelScope)
        }

    private fun updateScreenState(state: HomeScreenState) {
        _homeScreenState.value = state
    }

    fun sendIntent(intent: HomeScreenIntent) {
        when (intent) {
            is HomeScreenIntent.UpdateScreenState -> updateScreenState(intent.state)
        }
    }
}