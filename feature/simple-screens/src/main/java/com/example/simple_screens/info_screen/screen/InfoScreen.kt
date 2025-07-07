package com.example.simple_screens.info_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.design_system.theme.mColors
import com.example.simple_screens.common.SimpleTopBar
import com.example.simple_screens.info_screen.sections.Header
import com.example.simple_screens.info_screen.sections.InfoItemUi

data class InfoItem(
    val onClick: () -> Unit,
    val name: String,
    val label: String
)

val infoItems = listOf(
    InfoItem(
        onClick = {},
        name = "Версия",
        label = "Stable 1.0.0 (07.07.2025 03:00)"
    ),
    InfoItem(
        onClick = {},
        name = "Api",
        label = "Документация по api"
    ),
    InfoItem(
        onClick = {},
        name = "AniLiberty",
        label = "Сайт AniLiberty"
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    navController: NavController
) {
    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            SimpleTopBar(
                title = "Информация",
                scrollBehavior = topBarScrollBehavior,
                onBackClick = { navController.navigateUp() }
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
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { Header() }

                item { HorizontalDivider() }

                items(infoItems) { infoItem ->
                    InfoItemUi(
                        onClick = infoItem.onClick,
                        name = infoItem.name,
                        label = infoItem.label
                    )
                }
            }
        }
    }
}