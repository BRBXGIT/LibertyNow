package com.example.navbar_screens.more_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.common.auth.AuthIntent
import com.example.common.auth.AuthVM
import com.example.common.common.CommonIntent
import com.example.common.common.CommonVM
import com.example.design_system.theme.mColors
import com.example.navbar_screens.common.BottomNavBar
import com.example.navbar_screens.more_screen.sections.MoreLC
import com.example.navbar_screens.more_screen.sections.MoreScreenTopBar
import com.example.navbar_screens.more_screen.sections.QuitAccountAD

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen(
    commonVM: CommonVM,
    viewModel: MoreScreenVM,
    authVM: AuthVM,
    navController: NavController
) {
    val commonState by commonVM.commonState.collectAsStateWithLifecycle()
    val screenState by viewModel.moreScreenState.collectAsStateWithLifecycle()

    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
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
            MoreScreenTopBar(
                scrollBehavior = topBarScrollBehavior,
                onLogOutClick = {
                    viewModel.sendIntent(
                        MoreScreenIntent.UpdateScreenState(
                            screenState.copy(isQuitADVisible = true)
                        )
                    )
                },
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
            if (screenState.isQuitADVisible) {
                QuitAccountAD(
                    onConfirmClick = {
                        authVM.sendIntent(AuthIntent.ClearSessionToken)
                    },
                    onDismissRequest = {
                        viewModel.sendIntent(
                            MoreScreenIntent.UpdateScreenState(
                                screenState.copy(isQuitADVisible = false)
                            )
                        )
                    },
                )
            }

            MoreLC(
                onProjectTeamClick = {},
                onSupportClick = {},
                onSettingsClick = {}
            )
        }
    }
}

