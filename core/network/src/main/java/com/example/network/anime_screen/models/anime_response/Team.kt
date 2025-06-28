package com.example.network.anime_screen.models.anime_response

import kotlin.collections.List
import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("decor")
    val decor: List<String?> = listOf(),
    @SerializedName("editing")
    val editing: List<String?> = listOf(),
    @SerializedName("timing")
    val timing: List<String> = listOf(),
    @SerializedName("translator")
    val translator: List<String> = listOf(),
    @SerializedName("voice")
    val voice: List<String> = listOf()
)