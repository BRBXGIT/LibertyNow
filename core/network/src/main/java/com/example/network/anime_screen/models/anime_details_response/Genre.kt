package com.example.network.anime_screen.models.anime_details_response


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