package com.example.navbar_screens.home_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.design_system.theme.mColors

@Composable
fun HomeScreen(
    viewModel: HomeScreenVM
) {
    val titlesUpdates = viewModel.titlesUpdate.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(innerPadding)
        ) {
            LazyColumn {
                items(titlesUpdates.itemCount) {
                    Text(
                        text = it.toString()
                    )
                }
            }
        }
    }
}