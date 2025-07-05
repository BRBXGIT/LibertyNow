package com.example.local.db.lists_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ListsAnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: ListAnimeEntity)

    @Query("SELECT * FROM listanimeentity WHERE status = :status")
    suspend fun getAnimeByStatus(status: ListAnimeStatus): List<ListAnimeEntity>

    @Delete
    suspend fun deleteAnime(anime: ListAnimeEntity)
}
