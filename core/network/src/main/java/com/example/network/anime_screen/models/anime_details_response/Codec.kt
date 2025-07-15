package com.example.network.anime_screen.models.anime_details_response


import com.google.gson.annotations.SerializedName

data class Codec(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("label")
    val label: String = "",
    @SerializedName("label_color")
    val labelColor: String? = null,
    @SerializedName("label_is_visible")
    val labelIsVisible: Boolean = false,
    @SerializedName("value")
    val value: String = ""
)