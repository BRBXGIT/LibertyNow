package com.example.navbar_screens.home_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.common.functions.NetworkErrors
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.data.domain.HomeScreenRepo
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val repository: HomeScreenRepo,
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher,
    @Dispatcher(LibriaNowDispatchers.Main) private val dispatcherMain: CoroutineDispatcher
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
        .filter { it.isNotBlank() }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            repository.getTitlesByQuery(query).cachedIn(viewModelScope)
        }

    private fun updateScreenState(state: HomeScreenState) {
        _homeScreenState.value = state
    }

    private fun fetchRandomTitle(
        onComplete: (Int) -> Unit
    ) {
        viewModelScope.launch(dispatcherIo) {
            _homeScreenState.value = _homeScreenState.value.copy(isLoading = true)
            val response = repository.getRandomTitle()

            if (response.code() == 200) {
                val body = response.body()
                if (body == null) {
                    val label = processNetworkErrorsForUi(NetworkErrors.SERIALIZATION)

                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = label,
                            action = SnackbarAction(
                                name = "Retry",
                                action = { fetchRandomTitle(onComplete) }
                            )
                        )
                    )
                } else {
                    withContext(dispatcherMain) {
                        onComplete(body.id)
                    }
                }
            } else {
                val error = processNetworkErrors(response.code())
                val label = processNetworkErrorsForUi(error)

                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = label,
                        action = SnackbarAction(
                            name = "Retry",
                            action = { fetchRandomTitle(onComplete) }
                        )
                    )
                )
            }

            _homeScreenState.value = _homeScreenState.value.copy(isLoading = false)
        }
    }

    fun sendIntent(intent: HomeScreenIntent) {
        when (intent) {
            is HomeScreenIntent.UpdateScreenState -> updateScreenState(intent.state)
            is HomeScreenIntent.FetchRandomTitle -> fetchRandomTitle(intent.onComplete)
        }
    }
}