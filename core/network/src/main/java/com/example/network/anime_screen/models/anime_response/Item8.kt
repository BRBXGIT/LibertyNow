package com.example.network.anime_screen.models.anime_response


import com.google.gson.annotations.SerializedName

data class Item8(
    @SerializedName("downloads")
    val downloads: Int = 0,
    @SerializedName("episodes")
    val episodes: Episodes = Episodes(),
    @SerializedName("hash")
    val hash: String = "",
    @SerializedName("leechers")
    val leechers: Int = 0,
    @SerializedName("magnet")
    val magnet: String = "",
    @SerializedName("metadata")
    val metadata: Any? = Any(),
    @SerializedName("quality")
    val quality: Quality = Quality(),
    @SerializedName("raw_base64_file")
    val rawBase64File: Any? = Any(),
    @SerializedName("seeders")
    val seeders: Int = 0,
    @SerializedName("size_string")
    val sizeString: String = "",
    @SerializedName("torrent_id")
    val torrentId: Int = 0,
    @SerializedName("total_size")
    val totalSize: Long = 0,
    @SerializedName("uploaded_timestamp")
    val uploadedTimestamp: Int = 0,
    @SerializedName("url")
    val url: String = ""
)