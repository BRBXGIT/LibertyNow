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
import com.example.common.CommonIntent
import com.example.common.CommonState
import com.example.common.CommonVM
import com.example.design_system.theme.mColors
import com.example.local.datastore.auth.AuthState
import com.example.navbar_screens.common.BottomNavBar
import com.example.navbar_screens.likes_screen.sections.AuthBS
import com.example.navbar_screens.likes_screen.sections.LikesScreenTopBar
import com.example.navbar_screens.likes_screen.sections.LoggedOutSection

@Composable
fun LikesScreen(
    viewModel: LikesScreenVM,
    commonVM: CommonVM,
    commonState: CommonState,
    navController: NavController
) {
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

            when (screenState.isUserLoggedIn) {
                AuthState.Loading -> {}
                AuthState.LoggedIn -> {}
                AuthState.LoggedOut -> {
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