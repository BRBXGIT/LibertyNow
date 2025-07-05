package com.example.navbar_screens.lists_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.common.common.CommonIntent
import com.example.common.common.CommonVM
import com.example.design_system.theme.mColors
import com.example.navbar_screens.common.BottomNavBar
import com.example.navbar_screens.common.SearchableTopBar
import com.example.navbar_screens.lists_screen.sections.ListsTabRow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsScreen(
    viewModel: ListsScreenVM,
    commonVM: CommonVM,
    navController: NavController
) {
    val commonState by commonVM.commonState.collectAsStateWithLifecycle()
    val screenState by viewModel.listsScreenState.collectAsStateWithLifecycle()

    val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            SearchableTopBar(
                title = "Списки",
                query = screenState.query,
                isSearching = screenState.isSearching,
                isLoading = false,
                scrollBehavior = topBarScrollBehavior,
                onSearchClick = {
                    viewModel.sendIntent(
                        ListsScreenIntent.UpdateScreenState(
                            screenState.copy(isSearching = !screenState.isSearching)
                        )
                    )
                },
                onQueryInput = {
                    viewModel.sendIntent(
                        ListsScreenIntent.UpdateScreenState(
                            screenState.copy(query = it)
                        )
                    )
                },
                onClearClick = {
                    viewModel.sendIntent(
                        ListsScreenIntent.UpdateScreenState(
                            screenState.copy(query = "")
                        )
                    )
                }
            )
        },
        bottomBar = {
            BottomNavBar(
                onNavItemClick = { index, route ->
                    commonVM.sendIntent(
                        CommonIntent.UpdateState(
                            commonState.copy(selectedNavBarIndex = index)
                        )
                    )
                    navController.navigate(route)
                },
                selectedItemIndex = commonState.selectedNavBarIndex
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(innerPadding)
        ) {
            val animationScope = rememberCoroutineScope()
            val pagerState = rememberPagerState(
                pageCount = { screenState.animeByStatus.keys.size }
            )

            ListsTabRow(
                selectedTabIndex = screenState.selectedTabIndex,
                animeByStatus = screenState.animeByStatus,
                onTabClick = {
                    viewModel.sendIntent(
                        ListsScreenIntent.UpdateScreenState(
                            screenState.copy(selectedTabIndex = it)
                        )
                    )
                    animationScope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                },
            )
        }
    }
}