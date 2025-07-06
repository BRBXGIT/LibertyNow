package com.example.navbar_screens.lists_screen.sections

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import com.example.local.db.lists_db.ListAnimeStatus
import com.example.local.db.lists_db.ListsAnimeEntity

@Composable
fun ListPager(
    query: String,
    isSearching: Boolean,
    pagerState: PagerState,
    animeByStatus: Map<ListAnimeStatus, List<ListsAnimeEntity>>,
    onAnimeClick: (Int) -> Unit
) {
    val statuses = animeByStatus.keys.toList()

    HorizontalPager(state = pagerState) { page ->
        val status = statuses[page]
        val list = if (isSearching) {
            animeByStatus[status].orEmpty().filter {
                it.name.contains(query, ignoreCase = true) == true
            }
        } else {
            animeByStatus[status].orEmpty()
        }

        ListLVG(
            list = list,
            onAnimeClick = { onAnimeClick(it) }
        )
    }
}