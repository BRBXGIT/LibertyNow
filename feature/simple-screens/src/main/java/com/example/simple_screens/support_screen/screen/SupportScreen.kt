package com.example.simple_screens.support_screen.screen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mTypography
import com.example.simple_screens.support_screen.sections.AboutAniLibriaSection
import com.example.simple_screens.support_screen.sections.AniLibriaDefinitionSection
import com.example.simple_screens.support_screen.sections.DonateItemUi
import com.example.simple_screens.support_screen.sections.JoinTeamBS
import com.example.simple_screens.support_screen.sections.SupportScreenTopBar
import com.example.simple_screens.support_screen.sections.WhatsBadSection

data class DonateItem(
    val text: String,
    val icon: Int,
    val url: String
)

private val donateItems = listOf(
    DonateItem(
        text = "Оформить подписку на Boosty",
        icon = LibriaNowIcons.BoostyMulticolored,
        url = "https://boosty.to/anilibriatv"
    ),
    DonateItem(
        text = "Оформить подписку на Patreon",
        icon = LibriaNowIcons.PatreonMulticolored,
        url = "https://www.patreon.com/anilibria"
    ),
    DonateItem(
        text = "Денежный перевод через ЮMoney",
        icon = LibriaNowIcons.YoomoneyMulticolored,
        url = "https://yoomoney.ru/to/anilibria" // TODO change link to yoomoney
    ),
    DonateItem(
        text = "Донат на DonationAlerts",
        icon = LibriaNowIcons.DonationAlertsMulticolored,
        url = "https://www.donationalerts.com/r/anilibria"
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportScreen(navController: NavController) {
    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            SupportScreenTopBar(
                onNavIconClick = { navController.navigateUp() },
                scrollBehavior = topBarScrollBehavior
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
        ) {
            item { AniLibriaDefinitionSection() }
            item { AboutAniLibriaSection() }
            item { WhatsBadSection() }

            item {
                SectionTitle("Вы можете поддержать проект материально")
            }

            items(donateItems) { item ->
                DonateItemUi(
                    onClick = {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, item.url.toUri())
                        )
                    },
                    icon = item.icon,
                    text = item.text
                )
            }

            item {
                SectionTitle("А также нематериально")
            }

            item {
                var isJoinTeamBSOpened by rememberSaveable { mutableStateOf(false) }
                if (isJoinTeamBSOpened) {
                    JoinTeamBS(onDismissRequest = { isJoinTeamBSOpened = false })
                }

                DonateItemUi(
                    onClick = { isJoinTeamBSOpened = true },
                    icon = LibriaNowIcons.AniLibertyMulticolored,
                    text = "Вступить в команду AniLibria"
                )
            }

            item {
                Text(
                    text = "На данный момент все средства пойдут на поддержку сайта, онлайн плеера и в целом на развитие проекта. " +
                            "В дальнейшем планируется вернуть денежные премии для участников команды.",
                    style = mTypography.labelMedium,
                    modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                )
            }
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = mTypography.titleMedium.copy(
            color = mColors.primary,
            fontWeight = FontWeight.Bold
        )
    )
}