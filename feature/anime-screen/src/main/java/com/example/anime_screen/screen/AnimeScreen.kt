package com.example.anime_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.anime_screen.sections.AnimeScreenTopBar
import com.example.design_system.theme.mColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeScreen(
    animeId: Int,
    viewModel: AnimeScreenVM
) {
    // Fetch anime
    LaunchedEffect(animeId) {
        viewModel.sendIntent(
            AnimeScreenIntent.FetchAnime(animeId)
        )
    }

    val screenState by viewModel.animeScreenState.collectAsStateWithLifecycle()

    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            AnimeScreenTopBar(
                animeTitle = screenState.anime?.names?.ru,
                isLoading = screenState.isLoading,
                scrollBehavior = topBarScrollBehavior,
                isError = screenState.isError
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(innerPadding)
        ) {
            Text(
                text = animeId.toString()
            )
        }
    }
}