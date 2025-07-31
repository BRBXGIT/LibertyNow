package com.example.network.search_screen.models.anime_by_filters_request


import com.google.gson.annotations.SerializedName

data class Years(
    @SerializedName("from_year")
    val fromYear: Int = 0,
    @SerializedName("to_year")
    val toYear: Int = 0
)