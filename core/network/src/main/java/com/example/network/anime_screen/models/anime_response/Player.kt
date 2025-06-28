package com.example.network.anime_screen.models.anime_response


import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("alternative_player")
    val alternativePlayer: Any? = Any(),
    @SerializedName("episodes")
    val episodes: Episodes = Episodes(),
    @SerializedName("host")
    val host: String = "",
    @SerializedName("is_rutube")
    val isRutube: Boolean = false,
    @SerializedName("list")
    val list: Map<String, X1> = emptyMap(),
    @SerializedName("rutube")
    val rutube: Rutube = Rutube()
)