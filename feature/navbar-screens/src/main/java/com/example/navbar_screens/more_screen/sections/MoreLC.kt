package com.example.navbar_screens.more_screen.sections

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

data class MoreItem(
    val onClick: () -> Unit,
    val icon: Int,
    val label: String,
    val fromLink: Boolean
)

@Composable
fun MoreLC(
    onProjectTeamClick: () -> Unit,
    onSupportClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val context = LocalContext.current

    val aniLibertyItems = listOf(
        MoreItem(
            onClick = onProjectTeamClick,
            icon = LibriaNowIcons.Group,
            label = "Команда проекта",
            fromLink = false
        ),
        MoreItem(
            onClick = onSupportClick,
            icon = LibriaNowIcons.Gift,
            label = "Поддержать",
            fromLink = false
        )
    )

    val linksItems = listOf(
        MoreItem(
            onClick = { startLink(context, "https://vk.com/anilibria") },
            icon = LibriaNowIcons.VKMulticolored,
            label = "Группа VK",
            fromLink = true
        ),
        MoreItem(
            onClick = { startLink(context, "https://www.youtube.com/@anilibriatv") },
            icon = LibriaNowIcons.YouTubeMulticolored,
            label = "Канал YouTube",
            fromLink = true
        ),
        MoreItem(
            onClick = { startLink(context, "https://patreon.com/anilibria") },
            icon = LibriaNowIcons.PatreonMulticolored,
            label = "Patreon",
            fromLink = true
        ),
        MoreItem(
            onClick = { startLink(context, "https://t.me/anilibria_tv") },
            icon = LibriaNowIcons.TelegramMulticolored,
            label = "Канал Telegram",
            fromLink = true
        ),
        MoreItem(
            onClick = { startLink(context, "https://discord.gg/M6yCGeGN9B") },
            icon = LibriaNowIcons.DiscordMulticolored,
            label = "Чат Discord",
            fromLink = true
        ),
        MoreItem(
            onClick = { startLink(context, "https://anilibria.top") },
            icon = LibriaNowIcons.AniLiberty,
            label = "Сайт AniLiberty",
            fromLink = true
        ),
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(aniLibertyItems) { aniLibertyItem ->
            MoreItemUi(
                onClick = aniLibertyItem.onClick,
                icon = aniLibertyItem.icon,
                label = aniLibertyItem.label,
                fromLink = aniLibertyItem.fromLink
            )
        }

        item { HorizontalDivider() }

        item {
            MoreItemUi(
                onClick = onSettingsClick,
                icon = LibriaNowIcons.Settings,
                label = "Настройки",
                fromLink = false
            )
        }

        item { HorizontalDivider() }

        items(linksItems) { linkItem ->
            MoreItemUi(
                onClick = linkItem.onClick,
                icon = linkItem.icon,
                label = linkItem.label,
                fromLink = linkItem.fromLink
            )
        }
    }
}

@Composable
fun MoreItemUi(
    onClick: () -> Unit,
    icon: Int,
    label: String,
    fromLink: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(mShapes.small)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = if(fromLink) Color.Unspecified else mColors.onBackground
        )

        Text(
            text = label,
            style = mTypography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

private fun startLink(context: Context, link: String) {
    context.startActivity(
        Intent(
            Intent.ACTION_VIEW,
            link.toUri()
        )
    )
}

@Preview
@Composable
fun MoreItemUiPreview() {
    LibriaNowTheme {
        MoreItemUi(
            onClick = {},
            icon = LibriaNowIcons.Settings,
            label = "Настройки",
            fromLink = false
        )
    }
}