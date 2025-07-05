package com.example.local.db.watched_eps_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchedEpsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWatchedEpisode(episode: WatchedEpisodeEntity)

    @Query("SELECT episodeNumber FROM WatchedEpisodeEntity WHERE titleId = :titleId")
    fun getWatchedEpisodes(titleId: Int): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTitle(title: TitleEntity)
}