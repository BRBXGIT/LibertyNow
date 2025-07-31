package com.example.network.search_screen.models.anime_by_filters_request


import com.google.gson.annotations.SerializedName

data class AnimeByFiltersRequest(
    @SerializedName("f")
    val f: F = F(),
    @SerializedName("limit")
    val limit: Int = 0,
    @SerializedName("page")
    val page: Int = 0
)