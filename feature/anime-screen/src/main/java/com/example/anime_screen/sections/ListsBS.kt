package com.example.anime_screen.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.anime_screen.screen.AnimeScreenIntent
import com.example.anime_screen.screen.AnimeScreenState
import com.example.anime_screen.screen.AnimeScreenVM
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography
import com.example.local.db.lists_db.ListAnimeStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsBS(
    viewModel: AnimeScreenVM,
    screenState: AnimeScreenState
) {
    ModalBottomSheet(
        onDismissRequest = {
            viewModel.sendIntent(
                AnimeScreenIntent.UpdateScreenState(
                    screenState.copy(isListsBSOpened = false)
                )
            )
        },
        shape = mShapes.small,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(12.dp)
        ) {
            items(ListAnimeStatus.entries) { status ->
                val name = when(status) {
                    ListAnimeStatus.WATCHING -> "Смотрю"
                    ListAnimeStatus.COMPLETED -> "Просмотрено"
                    ListAnimeStatus.PLANNED -> "В планах"
                    ListAnimeStatus.ON_HOLD -> "Отложено"
                    ListAnimeStatus.DROPPED -> "Брошено"
                }

                ListItem(
                    name = name,
                    selected = status in screenState.currentListsAnimeIn,
                    onClick = {
                        val anime = screenState.anime!!
                        viewModel.sendIntent(
                            AnimeScreenIntent.MoveAnimeToList(
                                id = screenState.animeId,
                                poster = anime.posters.small.url,
                                genres = anime.genres.joinToString(", "),
                                name = anime.names.ru,
                                newStatus = status
                            )
                        )
                        viewModel.sendIntent(
                            AnimeScreenIntent.UpdateScreenState(
                                screenState.copy(isListsBSOpened = false)
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun ListItem(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(mShapes.small)
            .clickable {
                onClick()
            }
            .padding(12.dp)
    ) {
        Text(
            text = name,
            style = mTypography.bodyLarge
        )

        Icon(
            painter = painterResource(
                if (selected) LibriaNowIcons.CheckCircle else LibriaNowIcons.SingleArrowRightFilled
            ),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun ListItemPreview() {
    LibriaNowTheme {
        ListItem(
            name = "Просмотрено",
            selected = true,
            onClick = {}
        )
    }
}