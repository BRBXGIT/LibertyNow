package com.example.data.data

import com.example.data.domain.WatchedEpsRepo
import com.example.local.db.watched_eps_db.TitleEntity
import com.example.local.db.watched_eps_db.WatchedEpisodeEntity
import com.example.local.db.watched_eps_db.WatchedEpsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WatchedEpsRepoImpl @Inject constructor(
    private val dao: WatchedEpsDao
): WatchedEpsRepo {

    override suspend fun insertTitle(title: TitleEntity) {
        dao.insertTitle(title)
    }

    override suspend fun insertWatchedEpisode(episode: WatchedEpisodeEntity) {
        dao.insertWatchedEpisode(episode)
    }

    override fun getWatchedEpisodes(titleId: Int): Flow<List<Int>> {
        return dao.getWatchedEpisodes(titleId)
    }
}