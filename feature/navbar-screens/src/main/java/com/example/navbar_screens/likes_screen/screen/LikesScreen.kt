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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import com.example.common.auth.AuthIntent
import com.example.common.auth.AuthState
import com.example.common.common.CommonIntent
import com.example.common.common.CommonState
import com.example.design_system.sections.auth_bs.AuthBS
import com.example.design_system.snackbars.SnackbarObserver
import com.example.design_system.theme.mColors
import com.example.navbar_screens.common.BottomNavBar
import com.example.navbar_screens.common.SearchableTopBar
import com.example.navbar_screens.likes_screen.sections.LoggedInSection
import com.example.navbar_screens.likes_screen.sections.LoggedOutSection
import com.example.local.datastore.auth.LoggingState as UserAuthState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikesScreen(
    authState: AuthState,
    commonState: CommonState,
    screenState: LikesScreenState,
    navController: NavController,
    useExpressive: Boolean,
    onIntent: (LikesScreenIntent) -> Unit,
    onAuthIntent: (AuthIntent) -> Unit,
    onCommonIntent: (CommonIntent) -> Unit
) {
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
            SearchableTopBar(
                useExpressive = useExpressive,
                title = "Избранное",
                query = screenState.query,
                isSearching = screenState.isSearching,
                isLoading = authState.isLoading,
                scrollBehavior = topBarScrollBehavior,
                onSearchClick = { onIntent(LikesScreenIntent.ChangeIsSearching) },
                onQueryInput = { onIntent(LikesScreenIntent.ChangeQuery(it)) },
                onClearClick = { onIntent(LikesScreenIntent.ChangeQuery("")) }
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
                    onDismissRequest = { onAuthIntent(AuthIntent.ChangeIsAuthBsOpened) },
                    onPasswordChange = { onAuthIntent(AuthIntent.ChangePassword(it)) },
                    onEmailChange = { onAuthIntent(AuthIntent.ChangeEmail(it)) },
                    onAuthClick = { onAuthIntent(AuthIntent.GetSessionToken) },
                    onVisibleClick = { onAuthIntent(AuthIntent.ChangeIsPasswordVisible) }
                )
            }

            when (authState.isLogged) {
                UserAuthState.Loading -> {}
                UserAuthState.LoggedIn -> {
                    LoggedInSection(
                        screenState = screenState,
                        authState = authState,
                        navController = navController
                    )
                }
                UserAuthState.LoggedOut -> {
                    if (!authState.isLoading) {
                        LoggedOutSection(
                            onAuthClick = { onAuthIntent(AuthIntent.ChangeIsAuthBsOpened) }
                        )
                    }
                }
            }
        }
    }
}