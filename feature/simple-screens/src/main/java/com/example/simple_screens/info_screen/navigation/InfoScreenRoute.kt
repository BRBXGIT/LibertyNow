package com.example.simple_screens.info_screen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.simple_screens.info_screen.screen.InfoScreen
import kotlinx.serialization.Serializable

@Serializable
object InfoScreenRoute

fun NavGraphBuilder.infoScreen() = composable<InfoScreenRoute> {
    InfoScreen()
}