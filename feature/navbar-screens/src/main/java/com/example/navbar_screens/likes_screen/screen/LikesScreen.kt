package com.example.navbar_screens.likes_screen.screen

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.anime_screen.navigation.AnimeScreenRoute
import com.example.common.auth.AuthIntent
import com.example.common.auth.AuthVM
import com.example.common.common.CommonIntent
import com.example.common.common.CommonVM
import com.example.design_system.sections.auth_bs.AuthBS
import com.example.design_system.snackbars.SnackbarObserver
import com.example.design_system.theme.mColors
import com.example.navbar_screens.common.BottomNavBar
import com.example.navbar_screens.common.SearchableTopBar
import com.example.navbar_screens.likes_screen.sections.LikesLVG
import com.example.navbar_screens.likes_screen.sections.LoggedOutSection
import com.example.local.datastore.auth.LoggingState as UserAuthState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikesScreen(
    viewModel: LikesScreenVM,
    commonVM: CommonVM,
    authVM: AuthVM,
    navController: NavController
) {
    val authState by authVM.authState.collectAsStateWithLifecycle()
    val commonState by commonVM.commonState.collectAsStateWithLifecycle()

    val screenState by viewModel.likesScreenState.collectAsStateWithLifecycle()

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
                    commonVM.sendIntent(
                        CommonIntent.UpdateState(
                            commonState.copy(selectedNavBarIndex = index)
                        )
                    )
                    navController.navigate(route)
                }
            )
        },
        topBar = {
            SearchableTopBar(
                title = "Избранное",
                query = screenState.query,
                isSearching = screenState.isSearching,
                isLoading = authState.isLoading,
                scrollBehavior = topBarScrollBehavior,
                onSearchClick = {
                    viewModel.sendIntent(
                        LikesScreenIntent.UpdateScreenState(
                            screenState.copy(isSearching = !screenState.isSearching)
                        )
                    )
                },
                onQueryInput = {
                    viewModel.sendIntent(
                        LikesScreenIntent.UpdateScreenState(
                            screenState.copy(query = it)
                        )
                    )
                },
                onClearClick = {
                    viewModel.sendIntent(
                        LikesScreenIntent.UpdateScreenState(
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
            if (authState.isAuthBSOpened) {
                AuthBS(
                    email = authState.email,
                    password = authState.password,
                    isPasswordVisible = authState.isPasswordVisible,
                    incorrectEmail = authState.incorrectEmail,
                    incorrectPassword = authState.incorrectPassword,
                    onDismissRequest = {
                        authVM.sendIntent(
                            AuthIntent.UpdateAuthState(
                                state = authState.copy(isAuthBSOpened = false)
                            )
                        )
                    },
                    onPasswordChange = {
                        authVM.sendIntent(
                            AuthIntent.UpdateAuthState(
                                state = authState.copy(password = it)
                            )
                        )
                    },
                    onEmailChange = {
                        authVM.sendIntent(
                            AuthIntent.UpdateAuthState(
                                state = authState.copy(email = it)
                            )
                        )
                    },
                    onAuthClick = {
                        authVM.sendIntent(
                            AuthIntent.GetSessionToken
                        )
                    },
                    onVisibleClick = {
                        authVM.sendIntent(
                            AuthIntent.UpdateAuthState(
                                state = authState.copy(isPasswordVisible = !authState.isPasswordVisible)
                            )
                        )
                    }
                )
            }

            when (authState.isLogged) {
                UserAuthState.Loading -> {}
                UserAuthState.LoggedIn -> {
                    if (screenState.isSearching) {
                        LikesLVG(
                            likes = authState.likes.filter {
                                it.names.ru.contains(screenState.query, ignoreCase = true) == true
                            },
                            onAnimeClick = { navController.navigate(AnimeScreenRoute(it)) }
                        )
                    } else {
                        LikesLVG(
                            likes = authState.likes.reversed(),
                            onAnimeClick = { navController.navigate(AnimeScreenRoute(it)) }
                        )
                    }
                }
                UserAuthState.LoggedOut -> {
                    if (!authState.isLoading) {
                        LoggedOutSection(
                            onAuthClick = {
                                authVM.sendIntent(
                                    AuthIntent.UpdateAuthState(
                                        state = authState.copy(isAuthBSOpened = true)
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}