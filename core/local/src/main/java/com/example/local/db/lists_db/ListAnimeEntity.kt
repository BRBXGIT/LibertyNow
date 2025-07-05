package com.example.local.db.lists_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListAnimeEntity(
    @PrimaryKey
    val id: Int,
    val poster: String,
    val genres: String,
    val name: String,
    val status: ListAnimeStatus
)
