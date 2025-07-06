package com.example.data.domain

import com.example.local.db.lists_db.ListAnimeStatus
import com.example.local.db.lists_db.ListsAnimeEntity
import kotlinx.coroutines.flow.Flow

interface ListsRepo {

    suspend fun insertAnime(anime: ListsAnimeEntity)

    fun getAnimeByStatus(status: ListAnimeStatus): Flow<List<ListsAnimeEntity>>

    suspend fun moveAnimeToStatus(anime: ListsAnimeEntity)
}