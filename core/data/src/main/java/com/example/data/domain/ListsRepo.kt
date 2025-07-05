package com.example.data.domain

import com.example.local.db.lists_db.ListAnimeEntity
import com.example.local.db.lists_db.ListAnimeStatus

interface ListsRepo {

    suspend fun insertAnime(anime: ListAnimeEntity)

    suspend fun getAnimeByStatus(status: ListAnimeStatus): List<ListAnimeEntity>

    suspend fun deleteAnime(anime: ListAnimeEntity)
}