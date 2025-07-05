package com.example.simple_screens.project_team_screen.screen

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
import com.example.design_system.sections.error_section.ErrorSection
import com.example.design_system.snackbars.SnackbarObserver
import com.example.design_system.theme.mColors
import com.example.simple_screens.project_team_screen.sections.ProjectTeamLC
import com.example.simple_screens.project_team_screen.sections.ProjectTeamScreenTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectTeamScreen(
    viewModel: ProjectTeamScreenVM,
    navController: NavController
) {
    val screenState by viewModel.projectTeamScreenState.collectAsStateWithLifecycle()

    // Snackbars stuff
    val snackbarHostState = remember { SnackbarHostState() }
    SnackbarObserver(snackbarHostState)

    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            ProjectTeamScreenTopBar(
                loadingState = screenState.isLoading,
                scrollBehavior = topBarScrollBehavior,
                onBackClick = { navController.navigateUp() }
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
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            if (screenState.isError) {
                ErrorSection(modifier = Modifier.fillMaxSize())
            } else {
                val projectTeam = screenState.projectTeam
                projectTeam?.let {
                    ProjectTeamLC(
                        projectTeam = projectTeam,
                        bottomPadding = innerPadding.calculateBottomPadding()
                    )
                }
            }
        }
    }
}