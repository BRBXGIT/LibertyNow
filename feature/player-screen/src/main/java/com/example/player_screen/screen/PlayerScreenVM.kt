package com.example.player_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.design_system.theme.CommonConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerScreenVM @Inject constructor(
    val player: ExoPlayer,
    @Dispatcher(LibriaNowDispatchers.Default) private val dispatcherDefault: CoroutineDispatcher
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
        val mediaItems = _playerScreenState.value.links.map {
            MediaItem.fromUri(CommonConstants.BASE_SCHEME + _playerScreenState.value.host + it.hls.fhd)
        }
        player.setMediaItems(mediaItems, _playerScreenState.value.currentAnimeId, 0L)
        player.prepare()
        player.play()
        _playerScreenState.update { state ->
            state.copy(isPlaying = IsPlayingState.Playing)
        }
    }

    private fun updateIsControllerVisible() {
        viewModelScope.launch(dispatcherDefault) {
            when(_playerScreenState.value.isControllerVisible) {
                true -> {
                    _playerScreenState.update { state ->
                        state.copy(isControllerVisible = false)
                    }
                }
                false -> {
                    _playerScreenState.update { state ->
                        state.copy(isControllerVisible = true)
                    }
                    delay(4000)
                    _playerScreenState.update { state ->
                        state.copy(isControllerVisible = false)
                    }
                }
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
            state.copy(currentAnimeId = episodeId)
        }
    }

    fun sendIntent(intent: PlayerScreenIntent) {
        when(intent) {
            is PlayerScreenIntent.UpdateScreenState -> updateScreenState(intent.state)
            is PlayerScreenIntent.UpdateIsControllerVisible -> updateIsControllerVisible()

            is PlayerScreenIntent.PreparePlayer -> preparePlayer()
            is PlayerScreenIntent.ReleasePlayer -> player.release()
            is PlayerScreenIntent.PausePlayer -> pausePlayer()
            is PlayerScreenIntent.SkipEpisode -> skipEpisode(intent.forward)
            is PlayerScreenIntent.SetEpisode -> setEpisode(intent.episodeId)
        }
    }
}