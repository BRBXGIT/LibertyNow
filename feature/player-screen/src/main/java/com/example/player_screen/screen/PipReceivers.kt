package com.example.player_screen.screen

import android.app.PendingIntent
import android.app.RemoteAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.widget.Toast
import com.example.design_system.theme.LibriaNowIcons

class SkipEpisodeReceiver(): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val forward = intent?.getBooleanExtra("forward", true) != false

        val viewModel = PlayerScreenVM.instance

        if (viewModel != null) {
            viewModel.sendIntent(
                PlayerScreenIntent.SkipEpisode(forward)
            )
        } else {
            Toast.makeText(
                context,
                "Нужно немного подождать :)",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

fun createSkipEpisodePendingIntent(
    context: Context,
    requestCode: Int,
    forward: Boolean
): PendingIntent = PendingIntent.getBroadcast(
    context,
    requestCode,
    Intent(context, SkipEpisodeReceiver::class.java).apply {
        putExtra("forward", forward)
    },
    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
)

fun createSkipEpisodeRemoteAction(context: Context, forward: Boolean): RemoteAction {
    val iconRes = if (forward) LibriaNowIcons.ArrowRightFilled else LibriaNowIcons.ArrowLeftFilled
    val title = if (forward) "Меняет эпизод на следующий" else "Меняет эпизод на предыдущий"
    val description = if (forward) "Skip to the next episode" else "Skip to the previous episode"
    val requestCode = if (forward) 2 else 1

    return RemoteAction(
        Icon.createWithResource(context, iconRes),
        title,
        description,
        createSkipEpisodePendingIntent(context, requestCode, forward)
    )
}

class PlayPauseReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        PlayerScreenVM.instance?.sendIntent(
            PlayerScreenIntent.PausePlayer
        )
    }
}

fun createPlayPausePendingIntent(context: Context): PendingIntent = PendingIntent.getBroadcast(
    context,
    3,
    Intent(context, PlayPauseReceiver::class.java),
    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
)

fun createPlayPauseRemoteAction(context: Context, isPlaying: Boolean): RemoteAction {
    val iconRes = if (isPlaying) LibriaNowIcons.PauseFilled else LibriaNowIcons.PlayFilled
    val title = if (isPlaying) "Пауза" else "Воспроизвести"
    val description = if (isPlaying) "Приостановить воспроизведение" else "Начать воспроизведение"

    return RemoteAction(
        Icon.createWithResource(context, iconRes),
        title,
        description,
        createPlayPausePendingIntent(context)
    )
}