package com.example.navbar_screens.likes_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.common.auth.AuthVM
import com.example.common.common.CommonIntent
import com.example.common.common.CommonVM
import com.example.design_system.theme.mColors
import com.example.navbar_screens.common.BottomNavBar
import com.example.navbar_screens.likes_screen.sections.AuthBS
import com.example.navbar_screens.likes_screen.sections.LikesScreenTopBar
import com.example.navbar_screens.likes_screen.sections.LoggedOutSection
import com.example.local.datastore.auth.AuthState as UserAuthState

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
    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
            LikesScreenTopBar(
                isLoading = screenState.isLoading,
                onSearchClick = {}
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(innerPadding)
        ) {
            if (screenState.isAuthBSOpened) {
                AuthBS(
                    email = screenState.email,
                    password = screenState.password,
                    onDismissRequest = {
                        viewModel.sendIntent(
                            LikesScreenIntent.UpdateScreenState(
                                screenState.copy(isAuthBSOpened = false)
                            )
                        )
                    },
                    onPasswordChange = {
                        viewModel.sendIntent(
                            LikesScreenIntent.UpdateScreenState(
                                screenState.copy(password = it)
                            )
                        )
                    },
                    onEmailChange = {
                        viewModel.sendIntent(
                            LikesScreenIntent.UpdateScreenState(
                                screenState.copy(email = it)
                            )
                        )
                    },
                    onAuthClick = {},
                )
            }

            when (authState.isLogged) {
                UserAuthState.Loading -> {}
                UserAuthState.LoggedIn -> {}
                UserAuthState.LoggedOut -> {
                    LoggedOutSection(
                        onAuthClick = {
                            viewModel.sendIntent(
                                LikesScreenIntent.UpdateScreenState(
                                    screenState.copy(isAuthBSOpened = true)
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}