package com.example.simple_screens.info_screen.screen

import android.content.Intent
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    navController: NavController
) {
    val context = LocalContext.current

    val clipboardManager = LocalClipboardManager.current
    val versionText = "Версия"
    val versionCodeText = "Stable 1.1.0 (02.08.2025 18:47)"

    val infoItems = listOf(
        InfoItem(
            onClick = {
                clipboardManager.setText(AnnotatedString("$versionText $versionCodeText"))
            },
            name = versionText,
            label = versionCodeText
        ),
        InfoItem(
            onClick = {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        "https://anilibria.top/api/docs/v1".toUri()
                    )
                )
            },
            name = "Api",
            label = "Документация по api"
        ),
        InfoItem(
            onClick = {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        "anilibria.top".toUri()
                    )
                )
            },
            name = "AniLiberty",
            label = "Сайт AniLiberty"
        ),
    )

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