package com.example.network.home_screen.models.titles_updates_response


import com.google.gson.annotations.SerializedName

data class Posters(
    @SerializedName("medium")
    val medium: Medium = Medium(),
    @SerializedName("original")
    val original: Original = Original(),
    @SerializedName("small")
    val small: Small = Small()
)