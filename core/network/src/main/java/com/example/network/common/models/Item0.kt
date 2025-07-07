package com.example.network.common.models

import com.google.gson.annotations.SerializedName

data class Item0(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("genres")
    val genres: List<String> = listOf(),
    @SerializedName("names")
    val names: Names = Names(),
    @SerializedName("posters")
    val posters: Posters = Posters(),
)