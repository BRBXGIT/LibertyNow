package com.example.navbar_screens.lists_screen.screen

import com.example.local.db.lists_db.ListAnimeStatus
import com.example.local.db.lists_db.ListsAnimeEntity

data class ListsScreenState(
    val animeByStatus: Map<ListAnimeStatus, List<ListsAnimeEntity>> = emptyMap(),

    val isSearching: Boolean = false,
    val query: String = "",

    val selectedTabIndex: Int = 0
)
