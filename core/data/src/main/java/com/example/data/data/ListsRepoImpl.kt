package com.example.data.data

import com.example.data.domain.ListsRepo
import com.example.local.db.lists_db.ListAnimeEntity
import com.example.local.db.lists_db.ListAnimeStatus
import com.example.local.db.lists_db.ListsAnimeDao
import javax.inject.Inject

class ListsRepoImpl @Inject constructor(
    private val dao: ListsAnimeDao
): ListsRepo {

    override suspend fun insertAnime(anime: ListAnimeEntity) {
        dao.insertAnime(anime)
    }

    override suspend fun getAnimeByStatus(status: ListAnimeStatus): List<ListAnimeEntity> {
        return dao.getAnimeByStatus(status)
    }

    override suspend fun deleteAnime(anime: ListAnimeEntity) {
        dao.deleteAnime(anime)
    }
}