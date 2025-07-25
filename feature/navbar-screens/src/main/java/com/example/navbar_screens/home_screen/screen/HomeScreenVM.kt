package com.example.navbar_screens.home_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.common.functions.NetworkErrors
import com.example.data.domain.HomeScreenRepo
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
import com.example.network.common.models.anime_list_response.AnimeListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
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

    @OptIn(ExperimentalCoroutinesApi::class)
    val titlesByQuery = _homeScreenState
        .map { it.query }
        .filter { it.isNotBlank() }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            repository.getTitlesByQuery(query)
        }.cachedIn(viewModelScope)

    private fun updateScreenState(state: HomeScreenState) {
        _homeScreenState.value = state
    }

    private fun fetchTitlesUpdates() {
        viewModelScope.launch(dispatcherIo) {
            _homeScreenState.update { state ->
                state.copy(
                    isLoading = true,
                    isError = false
                )
            }

            val response = repository.getTitlesUpdates()
            if (response.error == NetworkErrors.SUCCESS) {
                _homeScreenState.update { state ->
                    state.copy(
                        isLoading = false,
                        titlesUpdates = response.response as AnimeListResponse,
                        isError = false
                    )
                }
            } else {
                _homeScreenState.update { state ->
                    state.copy(
                        isLoading = false,
                        isError = true
                    )
                }
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = response.label!!,
                        action = SnackbarAction(
                            name = "Retry",
                            action = { fetchTitlesUpdates() }
                        )
                    )
                )
            }
        }
    }

    private fun fetchRandomTitle(
        onComplete: (Int) -> Unit
    ) {
        viewModelScope.launch(dispatcherIo) {
            _homeScreenState.update { state ->
                state.copy(isLoading = true)
            }

            val response = repository.getRandomTitle()
            if (response.error == NetworkErrors.SUCCESS) {
                withContext(dispatcherMain) {
                    onComplete((response.response as AnimeListResponse)[0].id)
                }
            } else {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = response.label!!,
                        action = SnackbarAction(
                            name = "Retry",
                            action = { fetchRandomTitle(onComplete) }
                        )
                    )
                )
            }

            _homeScreenState.update { state ->
                state.copy(isLoading = false)
            }
        }
    }

    fun sendIntent(intent: HomeScreenIntent) {
        when (intent) {
            is HomeScreenIntent.UpdateScreenState -> updateScreenState(intent.state)
            is HomeScreenIntent.FetchRandomTitle -> fetchRandomTitle(intent.onComplete)
        }
    }

    init {
        fetchTitlesUpdates()
    }
}