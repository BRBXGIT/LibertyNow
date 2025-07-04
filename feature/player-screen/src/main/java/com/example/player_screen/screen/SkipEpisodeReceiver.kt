package com.example.player_screen.screen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

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