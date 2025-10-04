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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common.common.CommonIntent
import com.example.common.common.CommonVM
import com.example.common.utils.PagingStatesContainer
import com.example.common.utils.sendRetrySnackbar
import com.example.design_system.sections.error_section.ErrorSection
import com.example.design_system.snackbars.SnackbarObserver
import com.example.design_system.theme.mColors
import com.example.navbar_screens.common.BottomNavBar
import com.example.navbar_screens.search_screen.sections.AnimeByFiltersSection
import com.example.navbar_screens.search_screen.sections.FiltersBS
import com.example.navbar_screens.search_screen.sections.SearchScreenTopBar
import kotlinx.coroutines.launch

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

    val snackbarScope = rememberCoroutineScope()
    PagingStatesContainer(
        items = animeByFilters,
        onRetryRequest = { label, retry ->
            snackbarScope.launch { sendRetrySnackbar(label, retry) }
        },
        onLoadingChange = {
            viewModel.sendIntent(SearchScreenIntent.ChangeAnimeByFiltersLoading(it))
        },
        onError = {
            viewModel.sendIntent(SearchScreenIntent.ChangeAnimeByFiltersError(it))
        }
    )

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
                viewModel = viewModel,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(innerPadding)
        ) {
            if (screenState.isAnimeByFiltersError) {
                ErrorSection(modifier = Modifier.align(Alignment.Center))
            } else {
                AnimeByFiltersSection(
                    animeByFilters = animeByFilters,
                    navController = navController
                )
            }
        }
    }
}