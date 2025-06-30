package com.example.player_screen.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.design_system.theme.mColors
import com.example.network.anime_screen.models.anime_response.X1
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlayerScreen(
    currentAnimeId: Int,
    gsonLinks: String
) {
    val type = object : TypeToken<List<X1>>() {}.type
    val links: List<X1> = Gson().fromJson(gsonLinks, type)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
        ) {

        }
    }
}