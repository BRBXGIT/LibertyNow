package com.example.player_screen.sections

import android.graphics.Rect
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.player_screen.screen.videoViewBounds

@OptIn(UnstableApi::class)
@Composable
fun Player(
    exoPlayer: ExoPlayer,
    isCropped: Boolean
) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                resizeMode = if (isCropped) AspectRatioFrameLayout.RESIZE_MODE_ZOOM else AspectRatioFrameLayout.RESIZE_MODE_FIT
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        update = {
            it.resizeMode = if (isCropped) {
                AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            } else {
                AspectRatioFrameLayout.RESIZE_MODE_FIT
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                videoViewBounds = run {
                    val boundsInWindow = it.boundsInWindow()
                    Rect(boundsInWindow.left.toInt(), boundsInWindow.top.toInt(), boundsInWindow.right.toInt(), boundsInWindow.bottom.toInt())
                }
            }
    )
}