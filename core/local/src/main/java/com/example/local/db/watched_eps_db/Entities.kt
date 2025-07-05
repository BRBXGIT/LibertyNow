package com.example.local.db.watched_eps_db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class TitleEntity(
    @PrimaryKey
    val titleId: Int
)

@Entity(
    primaryKeys = ["titleId", "episodeNumber"],
    foreignKeys = [
        ForeignKey(
            entity = TitleEntity::class,
            parentColumns = ["titleId"],
            childColumns = ["titleId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["titleId"])]
)
data class WatchedEpisodeEntity(
    val titleId: Int,
    val episodeNumber: Int
)