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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.example.common.common.CommonIntent
import com.example.common.common.CommonState
import com.example.common.utils.PagingStatesContainer
import com.example.common.utils.sendRetrySnackbar
import com.example.design_system.sections.error_section.ErrorSection
import com.example.design_system.snackbars.SnackbarObserver
import com.example.design_system.theme.mColors
import com.example.navbar_screens.common.BottomNavBar
import com.example.navbar_screens.search_screen.sections.AnimeByFiltersSection
import com.example.navbar_screens.search_screen.sections.FiltersBS
import com.example.navbar_screens.search_screen.sections.SearchScreenTopBar
import com.example.network.common.models.anime_list_with_pagination_response.Data
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    useExpressive: Boolean,
    screenState: SearchScreenState,
    commonState: CommonState,
    animeByFilters: LazyPagingItems<Data>,
    onIntent: (SearchScreenIntent) -> Unit,
    onCommonIntent: (CommonIntent) -> Unit
) {
    val snackbarScope = rememberCoroutineScope()
    PagingStatesContainer(
        items = animeByFilters,
        onRetryRequest = { label, retry ->
            snackbarScope.launch { sendRetrySnackbar(label, retry) }
        },
        onLoadingChange = { onIntent(SearchScreenIntent.ChangeAnimeByFiltersLoading(it)) },
        onError = { onIntent(SearchScreenIntent.ChangeAnimeByFiltersError(it)) }
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
                    onCommonIntent(CommonIntent.ChangeNavIndex(index))
                    navController.navigate(route)
                }
            )
        },
        topBar = {
            SearchScreenTopBar(
                useExpressive = useExpressive,
                isLoading = screenState.isAnimeByFiltersLoading,
                scrollBehavior = topBarScrollBehavior,
                onFiltersClick = { onIntent(SearchScreenIntent.ChangeFiltersBSVisible) }
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
                onIntent = onIntent
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