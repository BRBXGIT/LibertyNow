package com.example.navbar_screens.home_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.items
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
import com.example.common.functions.NetworkException
import com.example.design_system.cards.AnimeCard
import com.example.design_system.sections.error_section.ErrorSection
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
import com.example.design_system.snackbars.SnackbarObserver
import com.example.design_system.theme.DesignUtils
import com.example.design_system.theme.mColors
import com.example.navbar_screens.common.BottomNavBar
import com.example.navbar_screens.common.SearchableTopBar
import com.example.navbar_screens.home_screen.sections.NothingHereSection
import com.example.navbar_screens.home_screen.sections.RandomAnimeButton
import com.example.navbar_screens.home_screen.sections.TitlesUpdatesLVG

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenVM,
    commonVM: CommonVM,
    navController: NavController
) {
    val titlesByQuery = viewModel.titlesByQuery.collectAsLazyPagingItems()

    val commonState by commonVM.commonState.collectAsStateWithLifecycle()
    val screenState by viewModel.homeScreenState.collectAsStateWithLifecycle()

    // Snackbars stuff
    val snackbarHostState = remember { SnackbarHostState() }
    SnackbarObserver(snackbarHostState)

    // Check titles by query errors
    LaunchedEffect(titlesByQuery.loadState, screenState.isSearching, screenState.query) {
        if (!screenState.isSearching) return@LaunchedEffect

        if (titlesByQuery.loadState.hasError) {
            val error = (titlesByQuery.loadState.refresh as LoadState.Error).error as NetworkException

            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = error.label,
                    action = SnackbarAction(
                        name = "Retry",
                        action = { titlesByQuery.retry() }
                    )
                )
            )
        }

        if ((titlesByQuery.loadState.refresh is LoadState.Loading) and (screenState.query.isNotBlank())) {
            viewModel.sendIntent(
                HomeScreenIntent.UpdateScreenState(
                    screenState.copy(
                        isLoading = true
                    )
                )
            )
        } else {
            viewModel.sendIntent(
                HomeScreenIntent.UpdateScreenState(
                    screenState.copy(
                        isLoading = false
                    )
                )
            )
        }
    }

    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            SearchableTopBar(
                title = "Последние обновления",
                query = screenState.query,
                isSearching = screenState.isSearching,
                isLoading = screenState.isLoading,
                scrollBehavior = topBarScrollBehavior,
                onSearchClick = {
                    viewModel.sendIntent(
                        HomeScreenIntent.UpdateScreenState(
                            screenState.copy(isSearching = !screenState.isSearching)
                        )
                    )
                },
                onQueryInput = { query ->
                    viewModel.sendIntent(
                        HomeScreenIntent.UpdateScreenState(
                            screenState.copy(query = query)
                        )
                    )
                },
                onClearClick = {
                    viewModel.sendIntent(
                        HomeScreenIntent.UpdateScreenState(
                            screenState.copy(query = "")
                        )
                    )
                }
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedItemIndex = commonState.selectedNavBarIndex,
                onNavItemClick = { index, route ->
                    commonVM.sendIntent(
                        CommonIntent.UpdateState(
                            commonState.copy(selectedNavBarIndex = index)
                        )
                    )
                    navController.navigate(route)
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(innerPadding)
        ) {
            if (screenState.isSearching) {
                if (screenState.query == "") {
                    NothingHereSection()
                } else if (titlesByQuery.loadState.refresh is LoadState.Error) {
                    ErrorSection(modifier = Modifier.align(Alignment.Center))
                } else {
                    TitlesUpdatesLVG {
                        items(titlesByQuery.itemCount, key = { it }) { index ->
                            val anime = titlesByQuery[index]

                            anime?.let {
                                AnimeCard(
                                    posterPath = DesignUtils.POSTERS_BASE_URL + anime.poster.optimized.preview,
                                    genresString = anime.genres.joinToString(", ") { it.name },
                                    title = anime.name.main,
                                    modifier = Modifier.animateItem(),
                                    onCardClick = { navController.navigate(AnimeScreenRoute(anime.id)) },
                                )
                            }
                        }
                    }
                }
            } else {
                if (screenState.isError) {
                    ErrorSection(modifier = Modifier.align(Alignment.Center))
                } else {
                    TitlesUpdatesLVG {
                        item(
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            RandomAnimeButton(
                                onClick = {
                                    viewModel.sendIntent(
                                        HomeScreenIntent.FetchRandomTitle(
                                            onComplete = { navController.navigate(AnimeScreenRoute(it)) }
                                        )
                                    )
                                }
                            )
                        }

                        items(screenState.titlesUpdates, key = { it.id }) { anime ->
                            AnimeCard(
                                posterPath = DesignUtils.POSTERS_BASE_URL + anime.poster.optimized.preview,
                                genresString = anime.genres.joinToString(", ") { it.name },
                                title = anime.name.main,
                                modifier = Modifier.animateItem(),
                                onCardClick = { navController.navigate(AnimeScreenRoute(anime.id)) },
                            )
                        }
                    }
                }
            }
        }
    }
}