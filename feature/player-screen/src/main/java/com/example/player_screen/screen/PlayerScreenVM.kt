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
import com.example.data.domain.WatchedEpsRepo
import com.example.local.db.watched_eps_db.WatchedEpisodeEntity
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
    private val watchedEpsRepo: WatchedEpsRepo,
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher,
    @Dispatcher(LibriaNowDispatchers.Default) private val dispatcherDefault: CoroutineDispatcher,
    @Dispatcher(LibriaNowDispatchers.Main) private val dispatcherMain: CoroutineDispatcher
): ViewModel() {
    companion object {
        var instance: PlayerScreenVM? = null
    }

    private val _playerScreenState = MutableStateFlow(PlayerScreenState())
    val playerScreenState = _playerScreenState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        PlayerScreenState()
    )

    private fun updateScreenState(state: PlayerScreenState) {
        _playerScreenState.value = state
    }

    private val playerListener = object : Player.Listener {
        override fun onTimelineChanged(timeline: Timeline, reason: Int) {
            if (player.contentDuration != C.TIME_UNSET) {
                addEpisodeToWatchedEps()
                player.playWhenReady = _playerScreenState.value.autoPlay != false
                _playerScreenState.update { state ->
                    state.copy(
                        duration = player.contentDuration,
                        isPlaying = if (_playerScreenState.value.autoPlay != false) {
                            IsPlayingState.Playing
                        } else {
                            IsPlayingState.Paused
                        },
                        timerStarted = false,
                        isSkipOpeningButtonVisible = false,
                        skipOpeningButtonTimer = 10
                    )
                }
            }
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            skipOpeningButtonTimerJob?.cancel()
            _playerScreenState.update { state ->
                state.copy(
                    timerStarted = false,
                    isSkipOpeningButtonVisible = false,
                    skipOpeningButtonTimer = 10
                )
            }

            if (reason == Player.MEDIA_ITEM_TRANSITION_REASON_AUTO) {
                val nextId = _playerScreenState.value.currentEpisodeId + 1
                if (nextId < _playerScreenState.value.episodes.size) {
                    _playerScreenState.update { state ->
                        state.copy(
                            currentEpisodeId = nextId,
                            currentEpisode = _playerScreenState.value.episodes[nextId],
                        )
                    }
                }
            }
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                Player.STATE_BUFFERING -> {
                    _playerScreenState.update { state ->
                        state.copy(isPlaying = IsPlayingState.Loading)
                    }
                }
                Player.STATE_READY -> {
                    if (player.playWhenReady) {
                        _playerScreenState.update { state ->
                            state.copy(isPlaying = IsPlayingState.Playing)
                        }
                    } else {
                        _playerScreenState.update { state ->
                            state.copy(isPlaying = IsPlayingState.Paused)
                        }
                    }
                }
                Player.STATE_ENDED -> {
                    _playerScreenState.update { state ->
                        state.copy(isPlaying = IsPlayingState.Paused)
                    }
                }
                Player.STATE_IDLE -> {
                    _playerScreenState.update { state ->
                        state.copy(isPlaying = IsPlayingState.Loading)
                    }
                }
            }
        }
    }

    private fun preparePlayer() {
        viewModelScope.launch(dispatcherIo) {
            combine(
                playerFeaturesRepository.autoPlay,
                playerFeaturesRepository.videoQuality
            ) { autoPlay, videoQuality ->
                autoPlay to videoQuality
            }.collect { (autoPlay, videoQuality) ->
                _playerScreenState.update { state ->
                    state.copy(
                        autoPlay = autoPlay != false,
                        videoQuality = videoQuality ?: 1080
                    )
                }

                val state = _playerScreenState.value
                val mediaItems = state.episodes.map {
                    when (videoQuality) {
                        480 -> MediaItem.fromUri(it.hls480)
                        720 -> MediaItem.fromUri(it.hls720)
                        else -> if (it.hls1080 != null) MediaItem.fromUri(it.hls1080!!) else MediaItem.fromUri(it.hls720) //TODO
                    }
                }

                withContext(dispatcherMain) {
                    player.clearMediaItems()
                    player.removeListener(playerListener)

                    player.setMediaItems(mediaItems, state.currentEpisodeId, state.currentPosition)
                    player.prepare()

                    player.addListener(playerListener)
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
                if (_playerScreenState.value.currentEpisodeId < _playerScreenState.value.episodes.size) {
                    player.seekToNextMediaItem()
                    _playerScreenState.update { state ->
                        state.copy(
                            currentEpisodeId = _playerScreenState.value.currentEpisodeId + 1,
                            timerStarted = false,
                            skipOpeningButtonTimer = 10
                        )
                    }
                }
            }
            false -> {
                if (_playerScreenState.value.currentEpisodeId > 0) {
                    player.seekToPreviousMediaItem()
                    _playerScreenState.update { state ->
                        state.copy(
                            currentEpisodeId = _playerScreenState.value.currentEpisodeId - 1,
                            timerStarted = false,
                            skipOpeningButtonTimer = 10
                        )
                    }
                }
            }
        }
    }

    private fun setEpisode(episodeId: Int) {
        player.seekTo(episodeId, 0L)
        _playerScreenState.update { state ->
            state.copy(
                currentEpisodeId = episodeId,
                isSelectEpisodeADVisible = false,
                timerStarted = false,
                skipOpeningButtonTimer = 10
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

    fun observePlayerFeatures() {
        viewModelScope.launch(dispatcherIo) {
            combine(
                playerFeaturesRepository.showSkipOpeningButton,
                playerFeaturesRepository.isCropped
            ) { showSkipOpeningButton, isCropped->
                showSkipOpeningButton to isCropped
            }.collect { (showSkipOpeningButton, isCropped) ->
                _playerScreenState.update { state ->
                    state.copy(
                        showSkipOpeningButton = showSkipOpeningButton != false,
                        isCropped = isCropped != false
                    )
                }
            }
        }
    }

    private var skipOpeningButtonTimerJob: Job? = null
    fun startSkipOpeningButtonTimer() {
        skipOpeningButtonTimerJob?.cancel()

        skipOpeningButtonTimerJob = viewModelScope.launch(dispatcherDefault) {
            _playerScreenState.update {
                it.copy(
                    isSkipOpeningButtonVisible = true,
                    timerStarted = true
                )
            }
            while (_playerScreenState.value.skipOpeningButtonTimer > 0) {
                if (_playerScreenState.value.isPlaying == IsPlayingState.Paused) {
                    delay(200)
                    continue
                }
                delay(1000)
                _playerScreenState.update {
                    val newTimer = it.skipOpeningButtonTimer - 1
                    it.copy(
                        skipOpeningButtonTimer = newTimer
                    )
                }
            }
            _playerScreenState.update {
                it.copy(
                    isSkipOpeningButtonVisible = false,
                    timerStarted = false
                )
            }
        }
    }

    private fun cancelSkipOpeningButtonTimer() {
        skipOpeningButtonTimerJob?.cancel()
        skipOpeningButtonTimerJob = null
        _playerScreenState.update { state ->
            state.copy(
                isSkipOpeningButtonVisible = false,
                timerStarted = false,
                skipOpeningButtonTimer = 10
            )
        }
    }

    private fun changePlayerFeature(featureType: FeatureType) {
        viewModelScope.launch(dispatcherIo) {
            when(featureType) {
                is FeatureType.AutoPlay -> playerFeaturesRepository.saveAutoplay(!_playerScreenState.value.autoPlay)
                is FeatureType.VideoQuality -> playerFeaturesRepository.saveVideoQuality(featureType.quality)
                is FeatureType.ShowSkipOpeningButton -> {
                    playerFeaturesRepository.saveShowSkipOpeningButton(!_playerScreenState.value.showSkipOpeningButton)
                }
                is FeatureType.IsCropped -> {
                    playerFeaturesRepository.saveIsCropped(!_playerScreenState.value.isCropped)
                }
            }
        }
    }

    private fun addEpisodeToWatchedEps() {
        viewModelScope.launch(dispatcherIo) {
            watchedEpsRepo.insertWatchedEpisode(
                WatchedEpisodeEntity(
                    titleId = _playerScreenState.value.animeId,
                    episodeNumber = _playerScreenState.value.currentEpisodeId
                )
            )
        }
    }

    fun sendIntent(intent: PlayerScreenIntent) {
        when(intent) {
            is PlayerScreenIntent.UpdateScreenState -> updateScreenState(intent.state)
            is PlayerScreenIntent.UpdateIsControllerVisible -> updateIsControllerVisible(intent.onStart, intent.onFinish)
            is PlayerScreenIntent.UpdateIsUnlockButtonVisible -> updateIsUnlockButtonVisible()

            is PlayerScreenIntent.PreparePlayer -> preparePlayer()
            is PlayerScreenIntent.ObservePlayerFeatures -> observePlayerFeatures()
            is PlayerScreenIntent.ReleasePlayer -> player.release()
            is PlayerScreenIntent.PausePlayer -> pausePlayer()
            is PlayerScreenIntent.SkipEpisode -> skipEpisode(intent.forward)
            is PlayerScreenIntent.SetEpisode -> setEpisode(intent.episodeId)
            is PlayerScreenIntent.SeekEpisode -> seekEpisode(intent.seekTo, intent.quickSeek)
            is PlayerScreenIntent.StartSkipOpeningButtonTimer -> startSkipOpeningButtonTimer()
            is PlayerScreenIntent.CancelSkipOpeningButtonTimer -> cancelSkipOpeningButtonTimer()

            is PlayerScreenIntent.ChangePlayerFeature -> changePlayerFeature(intent.feature)
        }
    }
}