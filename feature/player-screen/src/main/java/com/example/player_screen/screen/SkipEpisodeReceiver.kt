package com.example.player_screen.screen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SkipEpisodeReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("CCCC", "RECEIVER")
    }
}