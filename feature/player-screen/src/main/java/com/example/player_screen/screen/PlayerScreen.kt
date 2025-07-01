package com.example.player_screen.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.mColors
import com.example.network.anime_screen.models.anime_response.X1
import com.example.player_screen.sections.CentralButtonsSection
import com.example.player_screen.sections.Header
import com.example.player_screen.sections.Player
import com.example.player_screen.sections.SelectEpisodeAD
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlayerScreen(
    host: String,
    currentAnimeId: Int,
    gsonLinks: String,
    viewModel: PlayerScreenVM,
    navController: NavController
) {
    val type = object : TypeToken<List<X1>>() {}.type
    val links: List<X1> = Gson().fromJson(gsonLinks, type)
    val context = LocalContext.current

    val screenState by viewModel.playerScreenState.collectAsStateWithLifecycle()
    LaunchedEffect(currentAnimeId, links, host) {
        (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        viewModel.sendIntent(
            PlayerScreenIntent.UpdateScreenState(
                screenState.copy(
                    links = links,
                    currentAnimeId = currentAnimeId,
                    host = host,
                    currentLink = links[currentAnimeId]
                )
            )
        )
        viewModel.sendIntent(PlayerScreenIntent.PreparePlayer)
    }

    BackHandler {
        viewModel.sendIntent(PlayerScreenIntent.ReleasePlayer)
        (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        navController.navigateUp()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    viewModel.sendIntent(PlayerScreenIntent.UpdateIsControllerVisible)
                }
        ) {
            val player = viewModel.player
            Player(player)

            if (screenState.isSelectEpisodeADVisible) {
                SelectEpisodeAD(
                    currentAnimeId = screenState.currentAnimeId,
                    links = screenState.links,
                    onDismissRequest = {
                        viewModel.sendIntent(
                            PlayerScreenIntent.UpdateScreenState(
                                screenState.copy(isSelectEpisodeADVisible = false)
                            )
                        )
                    },
                    onConfirmClick = {
                        viewModel.sendIntent(PlayerScreenIntent.SetEpisode(it))
                        viewModel.sendIntent(
                            PlayerScreenIntent.UpdateScreenState(
                                screenState.copy(isSelectEpisodeADVisible = false)
                            )
                        )
                    }
                )
            }

            AnimatedVisibility(
                visible = screenState.isControllerVisible,
                enter = fadeIn(tween(CommonConstants.ANIMATION_DURATION)),
                exit = fadeOut(tween(CommonConstants.ANIMATION_DURATION)),
            ) {
                Header(
                    episode = screenState.currentAnimeId,
                    episodeTitle = screenState.links[screenState.currentAnimeId].name ?: "Без названия",
                    topPadding = innerPadding.calculateTopPadding(),
                    onBackClick = {
                        viewModel.sendIntent(PlayerScreenIntent.ReleasePlayer)
                        (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                        navController.navigateUp()
                    },
                    onPlaylistClick = {
                        viewModel.sendIntent(
                            PlayerScreenIntent.UpdateScreenState(
                                screenState.copy(isSelectEpisodeADVisible = true)
                            )
                        )
                    }
                )

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CentralButtonsSection(
                        isPlaying = screenState.isPlaying,
                        firstEpisode = screenState.currentAnimeId == 0,
                        lastEpisode = screenState.currentAnimeId == screenState.links.size,
                        onPreviousClick = {
                            viewModel.sendIntent(PlayerScreenIntent.SkipEpisode(false))
                        },
                        onPlayClick = {
                            viewModel.sendIntent(PlayerScreenIntent.PausePlayer)
                        },
                        onNextClick = {
                            viewModel.sendIntent(PlayerScreenIntent.SkipEpisode(true))
                        }
                    )
                }
            }
        }
    }
}