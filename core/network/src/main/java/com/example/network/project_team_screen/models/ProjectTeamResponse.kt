package com.example.network.project_team_screen.models


import com.google.gson.annotations.SerializedName

data class ProjectTeamResponse(
    @SerializedName("decor")
    val decor: List<String> = listOf(),
    @SerializedName("editing")
    val editing: List<String> = listOf(),
    @SerializedName("timing")
    val timing: List<String> = listOf(),
    @SerializedName("translator")
    val translator: List<String> = listOf(),
    @SerializedName("voice")
    val voice: List<String> = listOf()
)