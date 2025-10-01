package com.example.navbar_screens.search_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.anime_screen.navigation.AnimeScreenRoute
import com.example.common.common.CommonIntent
import com.example.common.common.CommonVM
import com.example.design_system.cards.AnimeCard
import com.example.design_system.sections.error_section.ErrorSection
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
import com.example.design_system.snackbars.SnackbarObserver
import com.example.design_system.theme.DesignUtils
import com.example.design_system.theme.mColors
import com.example.navbar_screens.common.AnimeLVGContainer
import com.example.navbar_screens.common.BottomNavBar
import com.example.navbar_screens.search_screen.sections.FiltersBS
import com.example.navbar_screens.search_screen.sections.SearchScreenTopBar
import com.example.network.common.utils.NetworkException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchScreenVM,
    commonVM: CommonVM,
    navController: NavController,
    useExpressive: Boolean
) {
    val animeByFilters = viewModel.animeByFilters.collectAsLazyPagingItems()

    val screenState by viewModel.searchScreenState.collectAsStateWithLifecycle()
    val commonState by commonVM.commonState.collectAsStateWithLifecycle()

    // Check load state
    LaunchedEffect(animeByFilters.loadState) {
        if (animeByFilters.loadState.hasError) {
            val error = (animeByFilters.loadState.refresh as LoadState.Error).error as NetworkException

            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = error.label,
                    action = SnackbarAction(
                        name = "Retry",
                        action = { animeByFilters.retry() }
                    )
                )
            )
        }

        if (animeByFilters.loadState.refresh is LoadState.Loading) {
            viewModel.sendIntent(SearchScreenIntent.ChangeAnimeByFiltersLoading)
        } else {
            viewModel.sendIntent(SearchScreenIntent.ChangeAnimeByFiltersLoading)
        }
    }

    // Snackbars stuff
    val snackbarHostState = remember { SnackbarHostState() }
    SnackbarObserver(snackbarHostState)

    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            BottomNavBar(
                selectedItemIndex = commonState.selectedNavBarIndex,
                onNavItemClick = { index, route ->
                    commonVM.sendIntent(CommonIntent.ChangeNavIndex(index))
                    navController.navigate(route)
                }
            )
        },
        topBar = {
            SearchScreenTopBar(
                useExpressive = useExpressive,
                isLoading = screenState.isAnimeByFiltersLoading,
                scrollBehavior = topBarScrollBehavior,
                onFiltersClick = { viewModel.sendIntent(SearchScreenIntent.ChangeFiltersBSVisible) }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        if (screenState.isFilterBSVisible) {
            FiltersBS(
                screenState = screenState,
                topInnerPadding = innerPadding.calculateTopPadding(),
                onDismissRequest = { viewModel.sendIntent(SearchScreenIntent.ChangeFiltersBSVisible) },
                onReleaseEndClick = { viewModel.sendIntent(SearchScreenIntent.ChangeReleaseEnd) },
                onSortClick = { viewModel.sendIntent(SearchScreenIntent.ChangeSortedBy(it)) },
                onSeasonClick = {
                    val currentSeasons = screenState.chosenSeasons.toMutableList()
                    viewModel.sendIntent(
                        SearchScreenIntent.ChangeChosenSeasons(
                            seasons = if (it in currentSeasons) currentSeasons - it else currentSeasons + it
                        )
                    )
                },
                onGenreClick = {
                    val currentGenres = screenState.chosenAnimeGenres.toMutableList()
                    viewModel.sendIntent(
                        SearchScreenIntent.ChangeChosenAnimeGenres(
                            genres = if (it in currentGenres) currentGenres - it else currentGenres + it
                        )
                    )
                },
                onGenresRetryClick = { viewModel.sendIntent(SearchScreenIntent.FetchAnimeGenres) },
                onFromYearChange = { viewModel.sendIntent(SearchScreenIntent.ChangeFromYear(it)) },
                onToYearChange = { viewModel.sendIntent(SearchScreenIntent.ChangeToYear(it)) },
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(innerPadding)
        ) {
            if (animeByFilters.loadState.refresh is LoadState.Error) {
                ErrorSection(modifier = Modifier.align(Alignment.Center))
            } else {
                AnimeLVGContainer {
                    items(animeByFilters.itemCount) { index ->
                        val anime = animeByFilters[index]

                        anime?.let {
                            AnimeCard(
                                posterPath = DesignUtils.POSTERS_BASE_URL + anime.poster.optimized.preview,
                                genresString = anime.genres.joinToString(", ") { it.name },
                                title = anime.name.main,
                                onCardClick = { navController.navigate(AnimeScreenRoute(it.id)) },
                                modifier = Modifier.animateItem()
                            )
                        }
                    }
                }
            }
        }
    }
}