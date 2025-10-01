package com.example.navbar_screens.search_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.data.domain.SearchScreenRepo
import com.example.network.common.utils.NetworkErrors
import com.example.network.search_screen.models.anime_by_filters_request.AnimeByFiltersRequest
import com.example.network.search_screen.models.anime_by_filters_request.F
import com.example.network.search_screen.models.anime_by_filters_request.Years
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
import javax.inject.Inject

@HiltViewModel
class SearchScreenVM @Inject constructor(
    private val repository: SearchScreenRepo,
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher,
): ViewModel() {
    private val _searchScreenState = MutableStateFlow(SearchScreenState())
    val searchScreenState = _searchScreenState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        SearchScreenState()
    )

    // === Private helpers ===
    private fun updateState(transform: (SearchScreenState) -> SearchScreenState) {
        _searchScreenState.update(transform)
    }

    // === Data ===
    private val _searchParams = _searchScreenState
        .map { state ->
            AnimeByFilterParams(
                chosenAnimeGenres = state.chosenAnimeGenres,
                sortedBy = state.sortedBy,
                chosenSeasons = state.chosenSeasons,
                releaseEnd = state.releaseEnd,
                toYear = state.toYear,
                fromYear = state.fromYear
            )
        }
        .distinctUntilChanged()

    @OptIn(ExperimentalCoroutinesApi::class)
    val animeByFilters = _searchParams
        .flatMapLatest { state ->
            val seasonCodes = state.chosenSeasons.map {
                when (it) {
                    Season.Winter -> "winter"
                    Season.Spring -> "spring"
                    Season.Summer -> "summer"
                    Season.Autumn -> "autumn"
                }
            }

            val publishStatuses = when(state.releaseEnd) {
                true -> "IS_NOT_ONGOING"
                else -> "IS_ONGOING"
            }
            val sorting = when(state.sortedBy) {
                SortedBy.Popularity -> "FRESH_AT_DESC"
                SortedBy.Novelty -> "RATING_DESC"
            }
            val requestBody = AnimeByFiltersRequest(
                f = F(
                    years = Years(fromYear = state.fromYear, toYear = state.toYear),
                    genres = state.chosenAnimeGenres,
                    publishStatuses = listOf(publishStatuses),
                    seasons = seasonCodes,
                    sorting = sorting,
                    types = listOf("TV", "WEB"),
                    ageRatings = emptyList(),
                    productionStatuses = emptyList(),
                )
            )

            repository.getAnimeByFilters(requestBody)
        }.cachedIn(viewModelScope)

    private fun fetchAnimeGenres() {
        viewModelScope.launch(dispatcherIo) {
            updateState { it.copy(isAnimeByFiltersLoading = true, isAnimeGenresError = false) }

            val response = repository.getAnimeGenres()
            if (response.error == NetworkErrors.SUCCESS) {
                updateState {
                    it.copy(
                        animeGenres = response.response!!,
                        isAnimeGenresLoading = false,
                        isAnimeGenresError = false
                    )
                }
            } else {
                updateState {
                    it.copy(
                        isAnimeGenresLoading = false,
                        isAnimeGenresError = true
                    )
                }
            }
        }
    }

    /// === Intents ===
    fun sendIntent(intent: SearchScreenIntent) {
        when (intent) {
            // === Data ===
            is SearchScreenIntent.FetchAnimeGenres -> fetchAnimeGenres()

            // === States ===
            SearchScreenIntent.ChangeFiltersBSVisible ->
                updateState { it.copy(isFilterBSVisible = !it.isFilterBSVisible) }
            SearchScreenIntent.ChangeReleaseEnd ->
                updateState { it.copy(releaseEnd = !it.releaseEnd) }
            is SearchScreenIntent.ChangeAnimeByFiltersLoading ->
                updateState { it.copy(isAnimeByFiltersLoading = intent.isLoading) }
            is SearchScreenIntent.ChangeSortedBy ->
                updateState { it.copy(sortedBy = intent.sortedBy) }
            is SearchScreenIntent.ChangeChosenSeasons ->
                updateState { it.copy(chosenSeasons = intent.seasons) }
            is SearchScreenIntent.ChangeChosenAnimeGenres ->
                updateState { it.copy(chosenAnimeGenres = intent.genres) }
            is SearchScreenIntent.ChangeFromYear ->
                updateState { it.copy(fromYear = intent.year) }
            is SearchScreenIntent.ChangeToYear ->
                updateState { it.copy(toYear = intent.year) }
        }
    }

    init {
        fetchAnimeGenres()
    }
}