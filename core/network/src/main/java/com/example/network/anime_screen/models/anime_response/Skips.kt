package com.example.network.anime_screen.models.anime_response

import kotlin.collections.List
import com.google.gson.annotations.SerializedName

data class Skips(
    @SerializedName("ending")
    val ending: List<Int?> = listOf(),
    @SerializedName("opening")
    val opening: List<Int?> = listOf()
)