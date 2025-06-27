package com.example.network.home_screen.models.titles_updates_response


import com.google.gson.annotations.SerializedName

data class TitlesUpdatesResponse(
    @SerializedName("list")
    val list: List<Item0> = listOf(),
    @SerializedName("pagination")
    val pagination: Pagination = Pagination()
)