package com.example.data.domain

import com.example.common.functions.NetworkResponse

interface ProjectTeamScreenRepo {

    suspend fun getProjectTeam(): NetworkResponse
}