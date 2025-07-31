package com.example.network.search_screen.models.anime_by_filters_request


import com.google.gson.annotations.SerializedName

data class F(
    @SerializedName("age_ratings")
    val ageRatings: List<String> = listOf(),
    @SerializedName("genres")
    val genres: List<Int> = listOf(),
    @SerializedName("production_statuses")
    val productionStatuses: List<String> = listOf(),
    @SerializedName("publish_statuses")
    val publishStatuses: List<String> = listOf(),
    @SerializedName("search")
    val search: String = "",
    @SerializedName("seasons")
    val seasons: List<String> = listOf(),
    @SerializedName("sorting")
    val sorting: String = "",
    @SerializedName("types")
    val types: List<String> = listOf(),
    @SerializedName("years")
    val years: Years = Years()
)