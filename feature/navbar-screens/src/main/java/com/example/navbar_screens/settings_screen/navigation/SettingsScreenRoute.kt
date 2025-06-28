package com.example.navbar_screens.settings_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.common.CommonState
import com.example.common.CommonVM
import com.example.navbar_screens.settings_screen.screen.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
data object SettingsScreenRoute

fun NavGraphBuilder.settingsScreen(
    commonVM: CommonVM,
    commonState: CommonState,
    navController: NavController
) = composable<SettingsScreenRoute> {
    SettingsScreen(
        commonVM = commonVM,
        commonState = commonState,
        navController = navController
    )
}