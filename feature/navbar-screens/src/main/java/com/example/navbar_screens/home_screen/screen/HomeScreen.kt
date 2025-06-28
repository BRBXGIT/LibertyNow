package com.example.navbar_screens.home_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common.functions.NetworkException
import com.example.design_system.snackbars.ObserveAsEvents
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
import com.example.design_system.theme.mColors
import com.example.navbar_screens.home_screen.sections.TitlesUpdatesLVG
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeScreenVM
) {
    val titlesUpdates = viewModel.titlesUpdate.collectAsLazyPagingItems()

    // Snackbars stuff
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    ObserveAsEvents(flow = SnackbarController.events, snackbarHostState) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Indefinite,
                withDismissAction = true
            )

            if(result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    // Check updates errors
    LaunchedEffect(titlesUpdates.loadState.hasError) {
        if (titlesUpdates.loadState.hasError) {
            val error = (titlesUpdates.loadState.refresh as LoadState.Error).error as NetworkException

            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = error.label,
                    action = SnackbarAction(
                        name = "Retry",
                        action = { titlesUpdates.retry() }
                    )
                )
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(innerPadding)
        ) {
            TitlesUpdatesLVG(
                titlesUpdates = titlesUpdates,
                onAnimeClick = {}
            )
        }
    }
}