package com.example.player_screen.screen

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.mColors
import com.example.network.anime_screen.models.anime_response.X1
import com.example.player_screen.sections.CentralButtonsSection
import com.example.player_screen.sections.Footer
import com.example.player_screen.sections.Header
import com.example.player_screen.sections.Player
import com.example.player_screen.sections.PlayerSettingsBS
import com.example.player_screen.sections.QualityBS
import com.example.player_screen.sections.QuickRewindSection
import com.example.player_screen.sections.SelectEpisodeAD
import com.example.player_screen.sections.SkipOpeningButton
import com.example.player_screen.sections.UnlockButton
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

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

    val systemUiController = rememberSystemUiController()
    val screenState by viewModel.playerScreenState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        systemUiController.isSystemBarsVisible = false
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
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
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
                    currentAnimeId = screenState.currentAnimeId,
                    links = screenState.links,
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
                PlayerSettingsBS(
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

            AnimatedVisibility(
                visible = (screenState.isControllerVisible or screenState.isUserSeeking) and !screenState.isLocked,
                enter = fadeIn(tween(CommonConstants.ANIMATION_DURATION)),
                exit = fadeOut(tween(CommonConstants.ANIMATION_DURATION)),
                modifier = Modifier.zIndex(2f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
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
                                PlayerScreenIntent.UpdateScreenState(
                                    screenState.copy(isCropped = !screenState.isCropped)
                                )
                            )
                        },
                    )
                }
            }

            if (screenState.isLocked) {
                AnimatedVisibility(
                    visible = screenState.isUnlockButtonVisible,
                    enter = fadeIn(tween(CommonConstants.ANIMATION_DURATION)),
                    exit = fadeOut(tween(CommonConstants.ANIMATION_DURATION)),
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

            AnimatedVisibility(
                visible = screenState.isSkipOpeningButtonVisible,
                enter = fadeIn(tween(CommonConstants.ANIMATION_DURATION)),
                exit = fadeOut(tween(CommonConstants.ANIMATION_DURATION)),
                modifier = Modifier.zIndex(2f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SkipOpeningButton(
                        onClick = {},
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