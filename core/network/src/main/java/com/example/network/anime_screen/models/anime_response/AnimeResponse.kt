package com.example.network.anime_screen.models.anime_response

import com.google.gson.annotations.SerializedName
import kotlin.collections.List

data class AnimeResponse(
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("genres")
    val genres: List<String> = emptyList<String>(),
    @SerializedName("in_favorites")
    val inFavorites: Int = 0,
    @SerializedName("names")
    val names: Names = Names(),
    @SerializedName("player")
    val player: Player = Player(),
    @SerializedName("posters")
    val posters: Posters = Posters(),
    @SerializedName("season")
    val season: Season = Season(),
    @SerializedName("status")
    val status: Status = Status(),
    @SerializedName("team")
    val team: Team = Team(),
    @SerializedName("torrents")
    val torrents: Torrents = Torrents(),
    @SerializedName("type")
    val type: Type = Type()
)