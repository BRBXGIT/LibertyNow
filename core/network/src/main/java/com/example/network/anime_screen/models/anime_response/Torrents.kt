package com.example.network.anime_screen.models.anime_response


import com.google.gson.annotations.SerializedName
import kotlin.collections.List

data class Torrents(
    @SerializedName("episodes")
    val episodes: Episodes = Episodes(),
    @SerializedName("list")
    val list: List<Item8> = listOf()
)