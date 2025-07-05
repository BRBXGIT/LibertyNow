package com.example.data.data

import com.example.common.functions.NetworkErrors
import com.example.common.functions.NetworkResponse
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.common.functions.processNetworkExceptions
import com.example.data.domain.ProjectTeamScreenRepo
import com.example.network.project_team_screen.api.ProjectTeamApiInstance
import javax.inject.Inject

class ProjectTeamScreenRepoImpl @Inject constructor(
    private val apiInstance: ProjectTeamApiInstance
): ProjectTeamScreenRepo {

    override suspend fun getProjectTeam(): NetworkResponse {
        return try {
            val response = apiInstance.getProjectTeam()

            if (response.code() == 200) {
                NetworkResponse(
                    response = response.body(),
                    error = NetworkErrors.SUCCESS
                )
            } else {
                val error = processNetworkErrors(response.code())
                val label = processNetworkErrorsForUi(error)
                NetworkResponse(
                    error = error,
                    label = label
                )
            }
        } catch (e: Exception) {
            processNetworkExceptions(e)
        }
    }
}