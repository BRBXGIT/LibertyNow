package com.example.player_screen.screen

import android.app.Activity
import android.app.PictureInPictureParams
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Rect
import android.util.Rational
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.design_system.theme.CommonConstants
import com.example.network.anime_screen.models.anime_details_response.Episode
import com.example.player_screen.sections.CentralButtonsSection
import com.example.player_screen.sections.Footer
import com.example.player_screen.sections.Header
import com.example.player_screen.sections.Player
import com.example.player_screen.sections.PlayerSettingSheet
import com.example.player_screen.sections.QualityBS
import com.example.player_screen.sections.QuickRewindSection
import com.example.player_screen.sections.SelectEpisodeAD
import com.example.player_screen.sections.SkipOpeningButton
import com.example.player_screen.sections.UnlockButton
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

var videoViewBounds = Rect()

@Composable
fun PlayerScreen(
    animeId: Int,
    currentEpisodeId: Int,
    gsonLinks: String,
    viewModel: PlayerScreenVM,
    navController: NavController
) {
    val type = object : TypeToken<List<Episode>>() {}.type
    val links: List<Episode> = Gson().fromJson(gsonLinks, type)
    val context = LocalContext.current

    val activity = context as Activity
    val isPipSupported by lazy {
        activity.packageManager.hasSystemFeature(
            PackageManager.FEATURE_PICTURE_IN_PICTURE
        )
    }

    val systemUiController = rememberSystemUiController()
    val screenState by viewModel.playerScreenState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        systemUiController.isSystemBarsVisible = false
        viewModel.sendIntent(
            PlayerScreenIntent.UpdateScreenState(
                screenState.copy(
                    episodes = links,
                    currentEpisodeId = currentEpisodeId,
                    currentEpisode = links[currentEpisodeId],
                    animeId = animeId
                )
            )
        )
        viewModel.sendIntent(PlayerScreenIntent.ObservePlayerFeatures)
        viewModel.sendIntent(PlayerScreenIntent.PreparePlayer)
        PlayerScreenVM.instance = viewModel
    }

    BackHandler {
        viewModel.sendIntent(PlayerScreenIntent.ReleasePlayer)
        systemUiController.isSystemBarsVisible = true
        context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        navController.navigateUp()
    }

    LaunchedEffect(screenState.isPlaying, screenState.currentEpisodeId) {
        if (activity.isInPictureInPictureMode) {
            val isPlayingNow = when (screenState.isPlaying) {
                IsPlayingState.Paused -> false
                else -> true
            }
            val firstEpisode = screenState.currentEpisodeId == 0
            val lastEpisode = screenState.currentEpisodeId == screenState.episodes.size - 1
            updatedPipParams(context, isPlayingNow, firstEpisode, lastEpisode)?.let { params ->
                activity.setPictureInPictureParams(params)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF000000))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    if (!screenState.isLocked) {
                        viewModel.sendIntent(
                            PlayerScreenIntent.UpdateIsControllerVisible(
                                onStart = { systemUiController.isSystemBarsVisible = true },
                                onFinish = {
                                    if (!screenState.isSelectEpisodeADVisible) {
                                        systemUiController.isSystemBarsVisible = false
                                    }
                                }
                            )
                        )
                    } else {
                        viewModel.sendIntent(PlayerScreenIntent.UpdateIsUnlockButtonVisible)
                    }
                }
        ) {
            val player = viewModel.player
            Player(player, screenState.isCropped)

            if (screenState.isSelectEpisodeADVisible) {
                SelectEpisodeAD(
                    currentAnimeId = screenState.currentEpisodeId,
                    links = screenState.episodes,
                    onDismissRequest = {
                        viewModel.sendIntent(
                            PlayerScreenIntent.UpdateScreenState(
                                screenState.copy(isSelectEpisodeADVisible = false)
                            )
                        )
                        systemUiController.isSystemBarsVisible = false
                    },
                    onConfirmClick = {
                        viewModel.sendIntent(PlayerScreenIntent.SetEpisode(it))
                    }
                )
            }

            if (screenState.isSettingsBSVisible) {
                PlayerSettingSheet(
                    autoPlay = screenState.autoPlay,
                    quality = screenState.videoQuality,
                    showSkipOpeningButton = screenState.showSkipOpeningButton,
                    onDismissRequest = {
                        viewModel.sendIntent(
                            PlayerScreenIntent.UpdateScreenState(
                                screenState.copy(isSettingsBSVisible = false)
                            )
                        )
                    },
                    onQualityClick = {
                        viewModel.sendIntent(
                            PlayerScreenIntent.UpdateScreenState(
                                screenState.copy(
                                    isQualityBSVisible = true,
                                    isSettingsBSVisible = false
                                )
                            )
                        )
                    },
                    onAutoPlayClick = {
                        viewModel.sendIntent(
                            PlayerScreenIntent.ChangePlayerFeature(FeatureType.AutoPlay)
                        )
                        viewModel.sendIntent(
                            PlayerScreenIntent.UpdateScreenState(
                                screenState.copy(isSettingsBSVisible = false)
                            )
                        )
                    },
                    onShowSkipOpeningButtonClick = {
                        viewModel.sendIntent(
                            PlayerScreenIntent.ChangePlayerFeature(FeatureType.ShowSkipOpeningButton)
                        )
                        viewModel.sendIntent(
                            PlayerScreenIntent.UpdateScreenState(
                                screenState.copy(isSettingsBSVisible = false)
                            )
                        )
                    }
                )
            }

            if (screenState.isQualityBSVisible) {
                QualityBS(
                    onClick = {
                        viewModel.sendIntent(
                            PlayerScreenIntent.ChangePlayerFeature(FeatureType.VideoQuality(it))
                        )
                        viewModel.sendIntent(
                            PlayerScreenIntent.UpdateScreenState(
                                screenState.copy(isQualityBSVisible = false)
                            )
                        )
                    },
                    onDismissRequest = {
                        viewModel.sendIntent(
                            PlayerScreenIntent.UpdateScreenState(
                                screenState.copy(isQualityBSVisible = false)
                            )
                        )
                    }
                )
            }

            AnimatedVisibilityContent(
                visible = (screenState.isControllerVisible or screenState.isUserSeeking) and !screenState.isLocked,
                modifier = Modifier.zIndex(2f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Header(
                        episode = screenState.currentEpisodeId,
                        episodeTitle = screenState.episodes[screenState.currentEpisodeId].name ?: "Без названия",
                        topPadding = innerPadding.calculateTopPadding(),
                        onBackClick = {
                            viewModel.sendIntent(PlayerScreenIntent.ReleasePlayer)
                            systemUiController.isSystemBarsVisible = true
                            context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
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

                    CentralButtonsSection(
                        isPlaying = screenState.isPlaying,
                        firstEpisode = screenState.currentEpisodeId == 0,
                        lastEpisode = screenState.currentEpisodeId == screenState.episodes.size - 1,
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

                    Footer(
                        duration = screenState.duration,
                        currentPosition = screenState.currentPosition,
                        bottomPadding = innerPadding.calculateBottomPadding(),
                        isSeeking = screenState.isUserSeeking,
                        isCropped = screenState.isCropped,
                        onValueChange = {
                            viewModel.sendIntent(
                                PlayerScreenIntent.UpdateScreenState(
                                    screenState.copy(
                                        isUserSeeking = true,
                                        currentPosition = it
                                    )
                                )
                            )
                        },
                        onValueChangeFinished = {
                            viewModel.sendIntent(
                                PlayerScreenIntent.SeekEpisode(it)
                            )
                        },
                        onLockClick = {
                            viewModel.sendIntent(
                                PlayerScreenIntent.UpdateScreenState(
                                    screenState.copy(
                                        isLocked = true,
                                        isControllerVisible = false
                                    )
                                )
                            )
                            systemUiController.isSystemBarsVisible = false
                        },
                        onSettingsClick = {
                            viewModel.sendIntent(
                                PlayerScreenIntent.UpdateScreenState(
                                    screenState.copy(isSettingsBSVisible = true)
                                )
                            )
                        },
                        onCropClick = {
                            viewModel.sendIntent(
                                PlayerScreenIntent.ChangePlayerFeature(FeatureType.IsCropped)
                            )
                        },
                        onPipClick = {
                            if (isPipSupported) {
                                viewModel.sendIntent(
                                    PlayerScreenIntent.UpdateScreenState(
                                        screenState.copy(isControllerVisible = false)
                                    )
                                )
                                val firstEpisode = screenState.currentEpisodeId == 0
                                val lastEpisode = screenState.currentEpisodeId == screenState.episodes.size - 1
                                updatedPipParams(
                                    context = context,
                                    isPlaying = when(screenState.isPlaying) {
                                        IsPlayingState.Paused -> false
                                        else -> true
                                    },
                                    firstEpisode = firstEpisode,
                                    lastEpisode = lastEpisode
                                )?.let { params ->
                                    activity.enterPictureInPictureMode(params)
                                }
                            }
                        }
                    )
                }
            }

            if (screenState.isLocked) {
                AnimatedVisibilityContent(
                    visible = screenState.isUnlockButtonVisible
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        UnlockButton(
                            onClick = {
                                viewModel.sendIntent(
                                    PlayerScreenIntent.UpdateScreenState(
                                        screenState.copy(
                                            isLocked = false,
                                            isUnlockButtonVisible = false
                                        )
                                    )
                                )
                            },
                            bottomPadding = innerPadding.calculateBottomPadding() + 12.dp
                        )
                    }
                }
            }

            LaunchedEffect(screenState.episodes, screenState.currentPosition) {
                if (screenState.episodes.isNotEmpty()) {
                    val opening = screenState.episodes[screenState.currentEpisodeId].opening
                    if ((opening.start != null) && (opening.stop != null)) {
                        val currentSec = screenState.currentPosition / 1000
                        val inOpening = currentSec in opening.start!!..opening.stop!!

                        if (inOpening) {
                            if (!screenState.timerStarted) {
                                viewModel.sendIntent(PlayerScreenIntent.StartSkipOpeningButtonTimer)
                            }
                        } else {
                            if (screenState.isSkipOpeningButtonVisible) {
                                viewModel.sendIntent(PlayerScreenIntent.CancelSkipOpeningButtonTimer)
                            }
                        }
                    }
                }
            }
            AnimatedVisibilityContent(
                visible = screenState.isSkipOpeningButtonVisible and screenState.showSkipOpeningButton,
                modifier = Modifier.zIndex(2f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SkipOpeningButton(
                        onClick = {
                            viewModel.sendIntent(
                                PlayerScreenIntent.SeekEpisode(
                                    seekTo = (screenState.episodes[screenState.currentEpisodeId].opening.stop!! * 1000).toLong()
                                )
                            )
                            viewModel.sendIntent(
                                PlayerScreenIntent.UpdateScreenState(
                                    screenState.copy(isSkipOpeningButtonVisible = false)
                                )
                            )
                        },
                        secondsLeft = screenState.skipOpeningButtonTimer
                    )
                }
            }

            QuickRewindSection(
                onLeftClick = {
                    viewModel.sendIntent(
                        PlayerScreenIntent.SeekEpisode(-5000, true)
                    )
                },
                onRightClick = {
                    viewModel.sendIntent(
                        PlayerScreenIntent.SeekEpisode(5000, true)
                    )
                },
                onSingleClick = {
                    if (!screenState.isLocked) {
                        viewModel.sendIntent(
                            PlayerScreenIntent.UpdateIsControllerVisible(
                                onStart = { systemUiController.isSystemBarsVisible = true },
                                onFinish = { systemUiController.isSystemBarsVisible = false }
                            )
                        )
                    } else {
                        viewModel.sendIntent(PlayerScreenIntent.UpdateIsUnlockButtonVisible)
                    }
                }
            )
        }
    }
}

@Composable
private fun AnimatedVisibilityContent(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(CommonConstants.ANIMATION_DURATION)),
        exit = fadeOut(tween(CommonConstants.ANIMATION_DURATION)),
        modifier = modifier
    ) {
        content()
    }
}

private fun updatedPipParams(
    context: Context,
    isPlaying: Boolean,
    firstEpisode: Boolean,
    lastEpisode: Boolean,
): PictureInPictureParams? {
    return PictureInPictureParams.Builder()
        .setSourceRectHint(videoViewBounds)
        .setAspectRatio(Rational(16, 9))
        .setActions(
            listOf(
                createSkipEpisodeRemoteAction(context, false, firstEpisode),
                createPlayPauseRemoteAction(context, isPlaying),
                createSkipEpisodeRemoteAction(context, true, lastEpisode)
            )
        )
        .build()
}