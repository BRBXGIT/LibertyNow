package com.example.player_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.exoplayer.ExoPlayer
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.data.domain.PlayerFeaturesRepo
import com.example.design_system.theme.CommonConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlayerScreenVM @Inject constructor(
    val player: ExoPlayer,
    private val playerFeaturesRepository: PlayerFeaturesRepo,
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher,
    @Dispatcher(LibriaNowDispatchers.Default) private val dispatcherDefault: CoroutineDispatcher,
    @Dispatcher(LibriaNowDispatchers.Main) private val dispatcherMain: CoroutineDispatcher
): ViewModel() {
    private val _playerScreenState = MutableStateFlow(PlayerScreenState())
    val playerScreenState = _playerScreenState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        PlayerScreenState()
    )

    private fun updateScreenState(state: PlayerScreenState) {
        _playerScreenState.value = state
    }

    private fun preparePlayer() {
        viewModelScope.launch(dispatcherIo) {
            combine(
                playerFeaturesRepository.autoPlay,
                playerFeaturesRepository.videoQuality
            ) { autoPlay, videoQuality ->
                autoPlay to videoQuality
            }.collect { (autoPlay, videoQuality) ->
                val mediaItems = _playerScreenState.value.links.map {
                    when(videoQuality) {
                        480 -> MediaItem.fromUri(CommonConstants.BASE_SCHEME + _playerScreenState.value.host + it.hls.sd)
                        720 -> MediaItem.fromUri(CommonConstants.BASE_SCHEME + _playerScreenState.value.host + it.hls.hd)
                        else -> MediaItem.fromUri(CommonConstants.BASE_SCHEME + _playerScreenState.value.host + it.hls.fhd)
                    }
                }
                withContext(dispatcherMain) {
                    player.setMediaItems(mediaItems, _playerScreenState.value.currentAnimeId, _playerScreenState.value.currentPosition)
                    player.playWhenReady = autoPlay != false
                    player.prepare()

                    player.addListener(object : Player.Listener {
                        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                            if (reason == Player.MEDIA_ITEM_TRANSITION_REASON_AUTO) {
                                _playerScreenState.value = PlayerScreenState(
                                    currentAnimeId = _playerScreenState.value.currentAnimeId + 1,
                                    currentLink = _playerScreenState.value.links[_playerScreenState.value.currentAnimeId + 1],
                                    host = _playerScreenState.value.host,
                                    links = _playerScreenState.value.links,
                                    isPlaying = IsPlayingState.Playing,
                                    duration = player.contentDuration
                                )
                            }
                        }

                        override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                            if (player.contentDuration != C.TIME_UNSET) {
                                _playerScreenState.update { state ->
                                    state.copy(
                                        duration = player.contentDuration,
                                        isPlaying = IsPlayingState.Playing
                                    )
                                }
                            }
                        }
                    })
                }

                trackCurrentPosition()
            }
        }
    }

    private fun trackCurrentPosition() {
        viewModelScope.launch {
            while (true) {
                if (!_playerScreenState.value.isUserSeeking) {
                    _playerScreenState.update { state ->
                        state.copy(currentPosition = player.currentPosition)
                    }
                }
                delay(500)
            }
        }
    }

    private var controllerVisibilityJob: Job? = null
    fun updateIsControllerVisible(
        onStart: () -> Unit,
        onFinish: () -> Unit
    ) {
        controllerVisibilityJob?.cancel()

        controllerVisibilityJob = viewModelScope.launch(dispatcherDefault) {
            when (_playerScreenState.value.isControllerVisible) {
                true -> {
                    _playerScreenState.update { state ->
                        state.copy(isControllerVisible = false)
                    }
                    withContext(dispatcherMain) { onFinish() }
                }
                false -> {
                    _playerScreenState.update { state ->
                        state.copy(isControllerVisible = true)
                    }
                    withContext(dispatcherMain) { onStart() }

                    delay(5000)

                    _playerScreenState.update { state ->
                        state.copy(isControllerVisible = false)
                    }
                    withContext(dispatcherMain) { onFinish() }
                }
            }
        }
    }

    private var unlockButtonVisibilityJob: Job? = null
    private fun updateIsUnlockButtonVisible() {
        unlockButtonVisibilityJob?.cancel()

        unlockButtonVisibilityJob = viewModelScope.launch(dispatcherDefault) {
            _playerScreenState.update { state ->
                state.copy(isUnlockButtonVisible = true)
            }
            delay(3000)
            _playerScreenState.update { state ->
                state.copy(isUnlockButtonVisible = false)
            }
        }
    }

    private fun pausePlayer() {
        when(_playerScreenState.value.isPlaying) {
            IsPlayingState.Loading -> {}
            IsPlayingState.Paused -> {
                player.play()
                _playerScreenState.update { state ->
                    state.copy(isPlaying = IsPlayingState.Playing)
                }
            }
            IsPlayingState.Playing -> {
                player.pause()
                _playerScreenState.update { state ->
                    state.copy(isPlaying = IsPlayingState.Paused)
                }
            }
        }
    }

    private fun skipEpisode(forward: Boolean) {
        when(forward) {
            true -> {
                if (_playerScreenState.value.currentAnimeId < _playerScreenState.value.links.size) {
                    player.seekToNextMediaItem()
                    _playerScreenState.update { state ->
                        state.copy(currentAnimeId = _playerScreenState.value.currentAnimeId + 1)
                    }
                }
            }
            false -> {
                if (_playerScreenState.value.currentAnimeId > 0) {
                    player.seekToPreviousMediaItem()
                    _playerScreenState.update { state ->
                        state.copy(currentAnimeId = _playerScreenState.value.currentAnimeId - 1)
                    }
                }
            }
        }
    }

    private fun setEpisode(episodeId: Int) {
        player.seekTo(episodeId, 0L)
        _playerScreenState.update { state ->
            state.copy(
                currentAnimeId = episodeId,
                isSelectEpisodeADVisible = false
            )
        }
    }

    private fun seekEpisode(
        seekTo: Long,
        quickSeek: Boolean = false
    ) {
        player.seekTo(
            when(quickSeek) {
                true -> _playerScreenState.value.currentPosition + seekTo
                false -> seekTo
            }
        )
        _playerScreenState.update { state ->
            state.copy(
                currentPosition = when(quickSeek) {
                    true -> _playerScreenState.value.currentPosition + seekTo
                    false -> seekTo
                },
                isUserSeeking = false
            )
        }
    }

    fun sendIntent(intent: PlayerScreenIntent) {
        when(intent) {
            is PlayerScreenIntent.UpdateScreenState -> updateScreenState(intent.state)
            is PlayerScreenIntent.UpdateIsControllerVisible -> updateIsControllerVisible(intent.onStart, intent.onFinish)
            is PlayerScreenIntent.UpdateIsUnlockButtonVisible -> updateIsUnlockButtonVisible()

            is PlayerScreenIntent.PreparePlayer -> preparePlayer()
            is PlayerScreenIntent.ReleasePlayer -> player.release()
            is PlayerScreenIntent.PausePlayer -> pausePlayer()
            is PlayerScreenIntent.SkipEpisode -> skipEpisode(intent.forward)
            is PlayerScreenIntent.SetEpisode -> setEpisode(intent.episodeId)
            is PlayerScreenIntent.SeekEpisode -> seekEpisode(intent.seekTo, intent.quickSeek)
        }
    }
}