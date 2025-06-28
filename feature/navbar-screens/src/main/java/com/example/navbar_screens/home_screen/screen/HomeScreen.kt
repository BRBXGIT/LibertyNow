package com.example.navbar_screens.home_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common.functions.NetworkException
import com.example.design_system.snackbars.ObserveAsEvents
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
import com.example.design_system.theme.mColors
import com.example.navbar_screens.home_screen.sections.HomeScreenTopBar
import com.example.navbar_screens.home_screen.sections.NothingHereSection
import com.example.navbar_screens.home_screen.sections.TitlesListLVG
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenVM
) {
    val titlesUpdates = viewModel.titlesUpdates.collectAsLazyPagingItems()
    val titlesByQuery = viewModel.titlesByQuery.collectAsLazyPagingItems()
    val screenState by viewModel.homeScreenState.collectAsStateWithLifecycle()

    // Snackbars stuff
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    ObserveAsEvents(flow = SnackbarController.events, snackbarHostState) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Indefinite,
                withDismissAction = true
            )

            if(result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    // Check titles updates errors
    LaunchedEffect(titlesUpdates.loadState) {
        if (titlesUpdates.loadState.hasError) {
            val error = (titlesUpdates.loadState.refresh as LoadState.Error).error as NetworkException

            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = error.label,
                    action = SnackbarAction(
                        name = "Retry",
                        action = { titlesUpdates.retry() }
                    )
                )
            )
        }

        if (titlesUpdates.loadState.refresh is LoadState.Loading) {
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

    // Check titles by query errors
    LaunchedEffect(titlesByQuery.loadState) {
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

        if (titlesByQuery.loadState.refresh is LoadState.Loading) {
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
            HomeScreenTopBar(
                screenState = screenState,
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
                } else {
                    TitlesListLVG(
                        titlesUpdates = titlesByQuery,
                        onAnimeClick = {},
                        showRandomButton = false
                    )
                }
            } else {
                TitlesListLVG(
                    titlesUpdates = titlesUpdates,
                    onAnimeClick = {},
                    onRandomClick = {
                        viewModel.sendIntent(
                            HomeScreenIntent.FetchRandomTitle(
                                onComplete = {

                                }
                            )
                        )
                    }
                )
            }
        }
    }
}