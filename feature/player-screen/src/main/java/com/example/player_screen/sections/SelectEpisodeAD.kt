package com.example.player_screen.sections

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography
import com.example.network.anime_screen.models.anime_response.X1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectEpisodeAD(
    currentAnimeId: Int,
    links: List<X1>,
    onDismissRequest: () -> Unit,
    onConfirmClick: (Int) -> Unit
) {
    var currentSelectedEpisodeIndex by rememberSaveable { mutableIntStateOf(currentAnimeId) }

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier.background(
            color = mColors.surfaceContainerHigh,
            shape = mShapes.small
        )
    ) {
        Column(
            modifier = Modifier.padding(CommonConstants.HORIZONTAL_PADDING.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Выбор серии",
                style = mTypography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                HorizontalDivider()

                LazyColumn(
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(links) { index, episode ->
                        EpisodeItem(
                            episodeTitle = episode.name ?: "Без названия",
                            episode = index + 1,
                            isSelected = currentSelectedEpisodeIndex == index,
                            onClick = { currentSelectedEpisodeIndex = index },
                        )
                    }
                }

                HorizontalDivider()
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.align(Alignment.End)
            ) {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(text = "Отмена")
                }

                TextButton(
                    onClick = {
                        onConfirmClick(currentSelectedEpisodeIndex)
                    }
                ) {
                    Text(text = "Выбрать")
                }
            }
        }
    }
}

@Composable
private fun EpisodeItem(
    episode: Int,
    episodeTitle: String,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(mShapes.extraSmall)
            .clickable { onClick() }
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$episode • $episodeTitle",
            style = mTypography.bodyMedium,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        val animatedIconAlpha by animateFloatAsState(
            targetValue = if (isSelected) 1.0f else 0f,
            animationSpec = tween(300),
            label = "Animated icon alpha"
        )
        Icon(
            painter = painterResource(LibriaNowIcons.CheckFilled),
            contentDescription = null,
            tint = mColors.primary,
            modifier = Modifier
                .size(16.dp)
                .alpha(animatedIconAlpha)
        )
    }
}

@Preview
@Composable
fun SelectEpisodeADPreview() {
    LibriaNowTheme {
        SelectEpisodeAD(
            currentAnimeId = 3,
            links = emptyList(),
            onDismissRequest = {},
            onConfirmClick = {}
        )
    }
}

@Preview
@Composable
fun EpisodeItemPreview() {
    LibriaNowTheme {
        EpisodeItem(
            episode = 2,
            episodeTitle = "Какое-то название",
            onClick = {},
            isSelected = true
        )
    }
}