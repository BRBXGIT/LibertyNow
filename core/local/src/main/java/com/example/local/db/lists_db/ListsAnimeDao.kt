package com.example.local.db.lists_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ListsAnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: ListsAnimeEntity)

    @Query("SELECT * FROM listsanimeentity WHERE status = :status")
    fun getAnimeByStatus(status: ListAnimeStatus): Flow<List<ListsAnimeEntity>>

    @Query("DELETE FROM listsanimeentity WHERE id = :id")
    suspend fun deleteAnimeFromList(id: Int)

    @Query("SELECT status FROM listsanimeentity WHERE id = :id")
    fun getStatusesByAnimeId(id: Int): Flow<List<ListAnimeStatus>>
}
