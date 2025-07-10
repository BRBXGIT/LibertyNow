package com.example.network.common.models.anime_list_response


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: Image = Image(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("total_releases")
    val totalReleases: Int = 0
)