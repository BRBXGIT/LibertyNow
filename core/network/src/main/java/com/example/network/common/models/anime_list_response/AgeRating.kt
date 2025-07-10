package com.example.network.common.models.anime_list_response


import com.google.gson.annotations.SerializedName

data class AgeRating(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("is_adult")
    val isAdult: Boolean = false,
    @SerializedName("label")
    val label: String = "",
    @SerializedName("value")
    val value: String = ""
)