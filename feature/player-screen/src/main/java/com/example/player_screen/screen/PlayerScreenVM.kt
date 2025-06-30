package com.example.player_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.design_system.theme.CommonConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlayerScreenVM @Inject constructor(
    val player: ExoPlayer
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
        val episodeLink = CommonConstants.BASE_SCHEME + _playerScreenState.value.host + _playerScreenState.value.currentLink.hls.fhd
        val mediaItem = MediaItem.fromUri(episodeLink)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    fun sendIntent(intent: PlayerScreenIntent) {
        when(intent) {
            is PlayerScreenIntent.UpdateScreenState -> updateScreenState(intent.state)
            is PlayerScreenIntent.PreparePlayer -> preparePlayer()
            is PlayerScreenIntent.ReleasePlayer -> player.release()
        }
    }
}