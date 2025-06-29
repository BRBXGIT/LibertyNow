package com.example.navbar_screens.search_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.common.functions.NetworkErrors
import com.example.data.domain.SearchScreenRepo
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenVM @Inject constructor(
    private val repository: SearchScreenRepo,
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {
    private val _searchScreenState = MutableStateFlow(SearchScreenState())
    val searchScreenState = _searchScreenState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        SearchScreenState()
    )

    private fun updateScreenState(state: SearchScreenState) {
        _searchScreenState.value = state
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val animeByFilters = _searchScreenState.flatMapLatest { state ->
        val seasonCodes = state.chosenSeasons.map {
            when (it) {
                Season.Winter -> 1
                Season.Spring -> 2
                Season.Summer -> 3
                Season.Autumn -> 4
            }
        }

        val queryParts = mutableListOf<String>()

        if (state.chosenAnimeYears.isNotEmpty()) {
            val yearsQuery = "({season.year}==${state.chosenAnimeYears.joinToString(" or {season.year}==")})"
            queryParts += yearsQuery
        }

        if (seasonCodes.isNotEmpty()) {
            val seasonQuery = "({season.code}==${seasonCodes.joinToString(" or {season.code}==")})"
            queryParts += seasonQuery
        }

        if (state.chosenAnimeGenres.isNotEmpty()) {
            val genresQuery = "(${state.chosenAnimeGenres.joinToString(" or ") { "\"$it\" in {genres}" }})"
            queryParts += genresQuery
        }

        // Always include releaseEnd
        queryParts += "(released==${state.releaseEnd})"

        val query = queryParts.joinToString(" and ")

        val orderBy = when (state.sortedBy) {
            SortedBy.Popularity -> "in_favorites"
            SortedBy.Novelty -> "-updated"
        }

        repository.getAnimeByFilters(
            query = query,
            orderBy = orderBy
        )
    }

    private fun fetchAnimeYears() {
        viewModelScope.launch(dispatcherIo) {
            _searchScreenState.update { state ->
                state.copy(
                    isAnimeYearsLoading = true,
                    isAnimeYearsError = false
                )
            }

            val response = repository.getAnimeYears()
            if (response.error == NetworkErrors.SUCCESS) {
                _searchScreenState.update { state ->
                    state.copy(
                        animeYears = (response.response as? List<*>)?.filterIsInstance<Int>()!!,
                        isAnimeYearsLoading = false,
                        isAnimeYearsError = false
                    )
                }
            } else {
                _searchScreenState.update { state ->
                    state.copy(
                        isAnimeYearsLoading = false,
                        isAnimeYearsError = true
                    )
                }
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = response.label!!,
                        action = SnackbarAction(
                            name = "Retry",
                            action = { fetchAnimeYears() }
                        )
                    )
                )
            }
        }
    }

    private fun fetchAnimeGenres() {
        viewModelScope.launch(dispatcherIo) {
            _searchScreenState.update { state ->
                state.copy(
                    isAnimeGenresLoading = true,
                    isAnimeGenresError = false
                )
            }

            val response = repository.getAnimeYears()
            if (response.error == NetworkErrors.SUCCESS) {
                _searchScreenState.update { state ->
                    state.copy(
                        animeGenres = (response.response as? List<*>)?.filterIsInstance<String>()!!,
                        isAnimeGenresLoading = false,
                        isAnimeGenresError = false
                    )
                }
            } else {
                _searchScreenState.update { state ->
                    state.copy(
                        isAnimeGenresLoading = false,
                        isAnimeGenresError = true
                    )
                }
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = response.label!!,
                        action = SnackbarAction(
                            name = "Retry",
                            action = { fetchAnimeYears() }
                        )
                    )
                )
            }
        }
    }

    fun sendIntent(intent: SearchScreenIntent) {
        when (intent) {
            is SearchScreenIntent.FetchAnimeYears -> fetchAnimeYears()
            is SearchScreenIntent.FetchAnimeGenres -> fetchAnimeGenres()
            is SearchScreenIntent.UpdateScreenState -> updateScreenState(intent.state)
        }
    }

    init {
        fetchAnimeYears()
        fetchAnimeGenres()
    }
}