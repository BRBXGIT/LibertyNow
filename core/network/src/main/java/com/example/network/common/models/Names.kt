package com.example.network.common.models


import com.google.gson.annotations.SerializedName

data class Names(
    @SerializedName("alternative")
    val alternative: Any? = null,
    @SerializedName("en")
    val en: String = "",
    @SerializedName("ru")
    val ru: String = ""
)