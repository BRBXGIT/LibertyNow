package com.example.anime_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.common.functions.NetworkErrors
import com.example.data.domain.AnimeScreenRepo
import com.example.data.domain.ListsRepo
import com.example.data.domain.WatchedEpsRepo
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
import com.example.local.db.lists_db.ListAnimeStatus
import com.example.local.db.lists_db.ListsAnimeEntity
import com.example.local.db.watched_eps_db.TitleEntity
import com.example.network.anime_screen.models.anime_details_response.AnimeDetailsResponse
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
    private val watchedEpsRepository: WatchedEpsRepo,
    private val listsRepository: ListsRepo,
    private val repository: AnimeScreenRepo,
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {
    private val _animeScreenState = MutableStateFlow(AnimeScreenState())
    val animeScreenState = _animeScreenState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1),
        AnimeScreenState()
    )

    private fun observeWatchedEps(titleId: Int) {
        viewModelScope.launch(dispatcherIo) {
            watchedEpsRepository.insertTitle(TitleEntity(titleId))
            watchedEpsRepository.getWatchedEpisodes(titleId).collect { eps ->
                _animeScreenState.update { state ->
                    state.copy(watchedEps = eps)
                }
            }
        }
    }

    private suspend fun observeStatuses(titleId: Int) {
        listsRepository.getStatusesByAnimeId(titleId).collect { lists ->
            _animeScreenState.update { state ->
                state.copy(currentListsAnimeIn = lists)
            }
        }
    }

    private fun fetchAnime(id: Int) {
        viewModelScope.launch(dispatcherIo) {
            _animeScreenState.update { state ->
                state.copy(
                    isLoading = true,
                    isError = false,
                    animeId = id
                )
            }

            val response = repository.getAnime(id)
            if (response.error == NetworkErrors.SUCCESS) {
                val anime = response.response as AnimeDetailsResponse
                _animeScreenState.update { state ->
                    state.copy(
                        anime = anime,
                        isError = false,
                        isLoading = false
                    )
                }
                observeStatuses(id)
            } else {
                _animeScreenState.update { state ->
                    state.copy(
                        isError = true,
                        isLoading = false
                    )
                }
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = response.label!!,
                        action = SnackbarAction(
                            name = "Retry",
                            action = { fetchAnime(id) }
                        )
                    )
                )
            }
        }
    }

    private fun moveAnimeToList(
        id: Int,
        poster: String,
        genres: String,
        name: String,
        newStatus: ListAnimeStatus
    ) {
        viewModelScope.launch(dispatcherIo) {
            listsRepository.moveAnimeToStatus(
                ListsAnimeEntity(
                    id = id,
                    poster = poster,
                    genres = genres,
                    name = name,
                    status = newStatus
                )
            )
        }
    }

    private fun updateScreenState(state: AnimeScreenState) {
        _animeScreenState.value = state
    }

    fun sendIntent(intent: AnimeScreenIntent) {
        when (intent) {
            is AnimeScreenIntent.FetchAnime -> fetchAnime(intent.id)
            is AnimeScreenIntent.ObserveWatchedEps -> observeWatchedEps(intent.id)

            is AnimeScreenIntent.MoveAnimeToList -> moveAnimeToList(intent.id, intent.poster, intent.genres, intent.name, intent.newStatus)

            is AnimeScreenIntent.UpdateScreenState -> updateScreenState(intent.state)
        }
    }
}