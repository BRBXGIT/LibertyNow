package com.example.anime_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.common.functions.NetworkErrors
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.data.domain.AnimeScreenRepo
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeScreenVM @Inject constructor(
    private val repository: AnimeScreenRepo,
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {
    private val _animeScreenState = MutableStateFlow(AnimeScreenState())
    val animeScreenState = _animeScreenState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        AnimeScreenState()
    )

    private fun fetchAnime(id: Int) {
        viewModelScope.launch(dispatcherIo) {
            _animeScreenState.update { state ->
                state.copy(
                    isLoading = true,
                    isError = false
                )
            }

            val response = repository.getAnime(id)
            if (response.code() == 200) {
                val body = response.body()
                if (body == null) {
                    _animeScreenState.update { state ->
                        state.copy(isError = true)
                    }
                    val label = processNetworkErrorsForUi(NetworkErrors.SERIALIZATION)

                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = label,
                            action = SnackbarAction(
                                name = "Retry",
                                action = { fetchAnime(id) }
                            )
                        )
                    )
                } else {
                    _animeScreenState.update { state ->
                        state.copy(anime = body)
                    }
                }
            } else {
                _animeScreenState.update { state ->
                    state.copy(isError = true)
                }
                val error = processNetworkErrors(response.code())
                val label = processNetworkErrorsForUi(error)

                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = label,
                        action = SnackbarAction(
                            name = "Retry",
                            action = { fetchAnime(id) }
                        )
                    )
                )
            }

            _animeScreenState.update { state ->
                state.copy(isLoading = false)
            }
        }
    }

    fun sendIntent(intent: AnimeScreenIntent) {
        when (intent) {
            is AnimeScreenIntent.FetchAnime -> fetchAnime(intent.id)
        }
    }
}