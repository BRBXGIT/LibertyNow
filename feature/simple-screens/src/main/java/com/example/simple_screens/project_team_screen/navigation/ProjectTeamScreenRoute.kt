package com.example.simple_screens.project_team_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.design_system.theme.CommonConstants
import com.example.simple_screens.project_team_screen.screen.ProjectTeamScreen
import com.example.simple_screens.project_team_screen.screen.ProjectTeamScreenVM
import kotlinx.serialization.Serializable

@Serializable
data object ProjectTeamScreenRoute

fun NavGraphBuilder.projectTeamScreen(
    navController: NavController
) = composable<ProjectTeamScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    val projectTeamScreenVM = hiltViewModel<ProjectTeamScreenVM>()

    ProjectTeamScreen(
        viewModel = projectTeamScreenVM,
        navController = navController
    )
}