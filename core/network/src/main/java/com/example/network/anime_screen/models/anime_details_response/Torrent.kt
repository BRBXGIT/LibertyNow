package com.example.network.anime_screen.models.anime_details_response


import com.google.gson.annotations.SerializedName

data class Torrent(
    @SerializedName("bitrate")
    val bitrate: Any? = Any(),
    @SerializedName("codec")
    val codec: Codec = Codec(),
    @SerializedName("color")
    val color: Color = Color(),
    @SerializedName("completed_times")
    val completedTimes: Int = 0,
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("filename")
    val filename: String = "",
    @SerializedName("hash")
    val hash: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("is_hardsub")
    val isHardsub: Boolean = false,
    @SerializedName("label")
    val label: String = "",
    @SerializedName("leechers")
    val leechers: Int = 0,
    @SerializedName("magnet")
    val magnet: String = "",
    @SerializedName("quality")
    val quality: Quality = Quality(),
    @SerializedName("release")
    val release: Release = Release(),
    @SerializedName("seeders")
    val seeders: Int = 0,
    @SerializedName("size")
    val size: Long = 0,
    @SerializedName("sort_order")
    val sortOrder: Int = 0,
    @SerializedName("type")
    val type: TypeXX = TypeXX(),
    @SerializedName("updated_at")
    val updatedAt: String = ""
)