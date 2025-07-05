package com.example.local.db.lists_db

import androidx.room.TypeConverter

class ListsDbConverters {
    @TypeConverter
    fun fromStatus(status: ListAnimeStatus): String = status.name

    @TypeConverter
    fun toStatus(value: String): ListAnimeStatus = ListAnimeStatus.valueOf(value)
}
