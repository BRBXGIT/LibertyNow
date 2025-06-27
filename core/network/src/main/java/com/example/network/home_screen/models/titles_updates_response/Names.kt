package com.example.network.home_screen.models.titles_updates_response


import com.google.gson.annotations.SerializedName

data class Names(
    @SerializedName("alternative")
    val alternative: Any? = null,
    @SerializedName("en")
    val en: String = "",
    @SerializedName("ru")
    val ru: String = ""
)