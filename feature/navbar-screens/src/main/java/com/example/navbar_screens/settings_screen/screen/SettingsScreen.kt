package com.example.navbar_screens.settings_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.common.common.CommonIntent
import com.example.common.common.CommonVM
import com.example.design_system.theme.mColors
import com.example.navbar_screens.common.BottomNavBar

@Composable
fun SettingsScreen(
    commonVM: CommonVM,
    navController: NavController
) {
    val commonState by commonVM.commonState.collectAsStateWithLifecycle()

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
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(innerPadding)
        ) {
            Text(
                text = "Settings screen"
            )
        }
    }
}