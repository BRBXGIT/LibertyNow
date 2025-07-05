package com.example.navbar_screens.lists_screen.screen

import com.example.local.db.lists_db.ListAnimeEntity
import com.example.local.db.lists_db.ListAnimeStatus

data class ListsScreenState(
    val animeByStatus: Map<ListAnimeStatus, List<ListAnimeEntity>> = emptyMap()
)
