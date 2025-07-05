package com.example.local.db.watched_eps_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TitleEntity::class, WatchedEpisodeEntity::class],
    version = 1
)
abstract class WatchedEpsDb: RoomDatabase() {
    abstract fun watchedEpsDao(): WatchedEpsDao
}