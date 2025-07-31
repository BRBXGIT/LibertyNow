package com.example.navbar_screens.search_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.common.functions.NetworkErrors
import com.example.data.domain.SearchScreenRepo
import com.example.network.common.models.common.Genre
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

    private fun updateScreenState(state: SearchScreenState) {
        _searchScreenState.value = state
    }

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
            _searchScreenState.update { state ->
                state.copy(
                    isAnimeGenresLoading = true,
                    isAnimeGenresError = false
                )
            }

            val response = repository.getAnimeGenres()
            if (response.error == NetworkErrors.SUCCESS) {
                _searchScreenState.update { state ->
                    state.copy(
                        animeGenres = (response.response as? List<*>)?.filterIsInstance<Genre>()!!,
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
            }
        }
    }

    fun sendIntent(intent: SearchScreenIntent) {
        when (intent) {
            is SearchScreenIntent.FetchAnimeGenres -> fetchAnimeGenres()
            is SearchScreenIntent.UpdateScreenState -> updateScreenState(intent.state)
        }
    }

    init {
        fetchAnimeGenres()
    }
}