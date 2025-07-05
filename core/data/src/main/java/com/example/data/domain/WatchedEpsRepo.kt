package com.example.data.domain

import com.example.local.db.watched_eps_db.TitleEntity
import com.example.local.db.watched_eps_db.WatchedEpisodeEntity
import kotlinx.coroutines.flow.Flow

interface WatchedEpsRepo {

    suspend fun insertWatchedEpisode(episode: WatchedEpisodeEntity)

    fun getWatchedEpisodes(titleId: Int): Flow<List<Int>>

    suspend fun insertTitle(title: TitleEntity)
}