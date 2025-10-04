package com.example.navbar_screens.home_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.common.utils.sendRetrySnackbar
import com.example.data.domain.HomeScreenRepo
import com.example.network.common.utils.NetworkErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val repository: HomeScreenRepo,
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher,
    @Dispatcher(LibriaNowDispatchers.Main) private val dispatcherMain: CoroutineDispatcher
): ViewModel() {
    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        HomeScreenState()
    )

    // === Data ===
    @OptIn(ExperimentalCoroutinesApi::class)
    val titlesByQuery = _homeScreenState
        .map { it.query }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            repository.getTitlesByQuery(query)
        }.cachedIn(viewModelScope)

    private fun fetchTitlesUpdates() {
        viewModelScope.launch(dispatcherIo) {
            updateState { it.copy(isLoading = true, isAnimeUpdatesError = false) }

            val response = repository.getTitlesUpdates()
            if (response.error == NetworkErrors.SUCCESS) {
                updateState { it.copy(isLoading = false, titlesUpdates = response.response!!, isAnimeUpdatesError = false) }
            } else {
                updateState { it.copy(isLoading = false, isAnimeUpdatesError = true) }
                sendRetrySnackbar(
                    label = response.label!!,
                    action = { fetchTitlesUpdates() }
                )
            }
        }
    }

    private fun fetchRandomTitle(
        onComplete: (Int) -> Unit
    ) {
        viewModelScope.launch(dispatcherIo) {
            updateState { it.copy(isLoading = true) }

            val response = repository.getRandomTitle()
            if (response.error == NetworkErrors.SUCCESS) {
                withContext(dispatcherMain) {
                    onComplete(response.response!![0].id)
                }
            } else {
                sendRetrySnackbar(
                    label = response.label!!,
                    action = { fetchRandomTitle(onComplete) }
                )
            }

            updateState { it.copy(isLoading = false) }
        }
    }

    // === Private helpers ===
    private fun updateState(transform: (HomeScreenState) -> HomeScreenState) {
        _homeScreenState.update(transform)
    }

    // === Intents ===
    fun sendIntent(intent: HomeScreenIntent) {
        when (intent) {
            // Data
            is HomeScreenIntent.FetchRandomTitle -> fetchRandomTitle(intent.onComplete)

            // States
            is HomeScreenIntent.ChangeIsLoading -> updateState { it.copy(isLoading = intent.isLoading) }
            HomeScreenIntent.ChangeIsSearching -> updateState { it.copy(isSearching = !it.isSearching) }
            is HomeScreenIntent.ChangeQuery -> updateState { it.copy(query = intent.query) }
            is HomeScreenIntent.ChangeIsAnimeByQueryError -> updateState { it.copy(isAnimeByQueryError = intent.isError) }
        }
    }

    init {
        fetchTitlesUpdates()
    }
}