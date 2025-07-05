package com.example.local.db.lists_db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ListsAnimeEntity::class],
    version = 1
)
@TypeConverters(ListsDbConverters::class)
abstract class ListsDb: RoomDatabase() {

    abstract fun listsAnimeDao(): ListsAnimeDao
}