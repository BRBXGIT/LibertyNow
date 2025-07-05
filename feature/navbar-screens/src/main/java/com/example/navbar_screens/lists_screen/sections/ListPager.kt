package com.example.navbar_screens.lists_screen.sections

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import com.example.local.db.lists_db.ListsAnimeEntity

@Composable
fun ListPager(
    pagerState: PagerState,
    list: List<ListsAnimeEntity>,
    onAnimeClick: (Int) -> Unit
) {
    HorizontalPager(state = pagerState) {
        ListLVG(
            list = list,
            onAnimeClick = { onAnimeClick(it) }
        )
    }
}