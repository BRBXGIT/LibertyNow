package com.example.network.project_team_screen.api

import com.example.network.project_team_screen.models.ProjectTeamResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProjectTeamApiInstance {

    @GET("team")
    suspend fun getProjectTeam(): Response<ProjectTeamResponse>
}