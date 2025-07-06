package com.example.data.data

import com.example.data.domain.ListsRepo
import com.example.local.db.lists_db.ListAnimeStatus
import com.example.local.db.lists_db.ListsAnimeDao
import com.example.local.db.lists_db.ListsAnimeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListsRepoImpl @Inject constructor(
    private val dao: ListsAnimeDao
): ListsRepo {

    override suspend fun insertAnime(anime: ListsAnimeEntity) {
        dao.insertAnime(anime)
    }

    override fun getAnimeByStatus(status: ListAnimeStatus): Flow<List<ListsAnimeEntity>> {
        return dao.getAnimeByStatus(status)
    }

    override suspend fun moveAnimeToStatus(anime: ListsAnimeEntity) {
        dao.deleteAnimeFromList(anime.id)
        dao.insertAnime(anime)
    }

    override fun getStatusesByAnimeId(id: Int): Flow<List<ListAnimeStatus>> {
        return dao.getStatusesByAnimeId(id)
    }
}