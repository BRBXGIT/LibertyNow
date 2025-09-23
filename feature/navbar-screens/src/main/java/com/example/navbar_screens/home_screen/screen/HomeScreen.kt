package com.example.navbar_screens.home_screen.screen

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.anime_screen.navigation.AnimeScreenRoute
import com.example.common.common.CommonIntent
import com.example.common.common.CommonVM
import com.example.common.utils.PagingStatesContainer
import com.example.common.utils.sendRetrySnackbar
import com.example.design_system.snackbars.SnackbarObserver
import com.example.design_system.theme.mColors
import com.example.navbar_screens.common.BottomNavBar
import com.example.navbar_screens.common.SearchableTopBar
import com.example.navbar_screens.home_screen.sections.AnimeUpdatesContent
import com.example.navbar_screens.home_screen.sections.SearchingContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenVM,
    commonVM: CommonVM,
    navController: NavController,
    useExpressive: Boolean
) {
    val titlesByQuery = viewModel.titlesByQuery.collectAsLazyPagingItems()

    val commonState by commonVM.commonState.collectAsStateWithLifecycle()
    val screenState by viewModel.homeScreenState.collectAsStateWithLifecycle()

    // Snackbars stuff
    val snackbarHostState = remember { SnackbarHostState() }
    SnackbarObserver(snackbarHostState)

    val snackbarScope = rememberCoroutineScope()
    PagingStatesContainer(
        items = titlesByQuery,
        onRetryRequest = { label, retry ->
            snackbarScope.launch { sendRetrySnackbar(label, retry) }
        },
        onLoadingChange = {
            viewModel.sendIntent(HomeScreenIntent.ChangeIsLoading(it))
        }
    )

    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            SearchableTopBar(
                useExpressive = useExpressive,
                title = "Последние обновления",
                query = screenState.query,
                isSearching = screenState.isSearching,
                isLoading = screenState.isLoading,
                scrollBehavior = topBarScrollBehavior,
                onSearchClick = { viewModel.sendIntent(HomeScreenIntent.ChangeIsSearching) },
                onQueryInput = { query -> viewModel.sendIntent(HomeScreenIntent.ChangeQuery(query)) },
                onClearClick = { viewModel.sendIntent(HomeScreenIntent.ChangeQuery("")) }
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedItemIndex = commonState.selectedNavBarIndex,
                onNavItemClick = { index, route ->
                    commonVM.sendIntent(CommonIntent.ChangeNavIndex(index))
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
                SearchingContent(
                    query = screenState.query,
                    titlesByQuery = titlesByQuery,
                    onCardClick = { navController.navigate(AnimeScreenRoute(it)) }
                )
            } else {
                AnimeUpdatesContent(
                    screenState = screenState,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }
}