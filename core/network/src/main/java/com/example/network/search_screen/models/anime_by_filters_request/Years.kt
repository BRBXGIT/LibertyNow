package com.example.network.search_screen.models.anime_by_filters_request


import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Years(
    @SerializedName("from_year")
    val fromYear: Int = 0,
    @SerializedName("to_year")
    val toYear: Int = LocalDateTime.now().year
)