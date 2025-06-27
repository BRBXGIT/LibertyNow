package com.example.network.home_screen.models.titles_updates_response


import com.google.gson.annotations.SerializedName

data class Item0(
    @SerializedName("genres")
    val genres: List<String> = listOf(),
    @SerializedName("names")
    val names: Names = Names(),
    @SerializedName("posters")
    val posters: Posters = Posters()
)