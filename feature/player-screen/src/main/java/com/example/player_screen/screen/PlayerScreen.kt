package com.example.player_screen.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.design_system.theme.mColors
import com.example.network.anime_screen.models.anime_response.X1
import com.example.player_screen.sections.Player
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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

    val screenState by viewModel.playerScreenState.collectAsStateWithLifecycle()
    LaunchedEffect(currentAnimeId, links, host) {
        (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
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
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
        ) {
            val player = viewModel.player
            Player(player)
        }
    }
}