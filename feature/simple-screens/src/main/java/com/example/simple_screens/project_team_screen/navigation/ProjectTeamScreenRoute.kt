package com.example.simple_screens.project_team_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.design_system.theme.CommonConstants
import com.example.simple_screens.project_team_screen.screen.ProjectTeamScreen
import kotlinx.serialization.Serializable

@Serializable
data object ProjectTeamScreenRoute

fun NavGraphBuilder.projectTeamScreen() = composable<ProjectTeamScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    ProjectTeamScreen()
}