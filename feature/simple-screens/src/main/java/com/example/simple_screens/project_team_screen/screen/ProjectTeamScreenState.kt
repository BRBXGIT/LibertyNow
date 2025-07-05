package com.example.simple_screens.project_team_screen.screen

import com.example.network.project_team_screen.models.ProjectTeamResponse

data class ProjectTeamScreenState(
    val projectTeam: ProjectTeamResponse? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)