package com.example.simple_screens.support_screen.sections

import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

data class Role(val name: String, val description: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinTeamBS(onDismissRequest: () -> Unit) {
    ModalBottomSheet(
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        onDismissRequest = onDismissRequest,
        shape = mShapes.small
    ) {
        val roles = listOf(
            Role("Войсер", "озвучивает персонажей"),
            Role("Саббер", "переводит субтитры"),
            Role("Технарь", "делает хардсаб, накладывает звуковые эффекты и сводит звуковые дорожки"),
            Role("Сидер", "раздаёт серии через torrent")
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Вступить в команду AniLibria",
                style = mTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(text = rolesDescription(roles))

            Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
                LinkButton(
                    icon = painterResource(LibriaNowIcons.TelegramMulticolored),
                    text = "Подать заявку через Telegram",
                    url = "https://t.me/joinlibria_bot"
                )
                LinkButton(
                    icon = painterResource(LibriaNowIcons.YouTubeMulticolored),
                    text = "Первые шаги для озвучивания аниме",
                    url = "https://youtu.be/J4AKmleW7Ls"
                )
            }

            Text(
                text = "Не знаете с чего начать озвучивать аниме? " +
                        "Советуем ознакомиться с этим видео от Lupin (руководитель проекта)"
            )
        }
    }
}

@Composable
fun rolesDescription(roles: List<Role>): AnnotatedString {
    val baseTextStyle = SpanStyle(
        fontSize = mTypography.bodyLarge.fontSize,
        fontFamily = mTypography.bodyLarge.fontFamily
    )
    val roleStyle = baseTextStyle.copy(color = mColors.primary)

    return buildAnnotatedString {
        withStyle(baseTextStyle) {
            append("Какие роли у нас есть:\n")
        }
        roles.forEach { role ->
            withStyle(roleStyle) {
                append("${role.name} ")
            }
            withStyle(baseTextStyle) {
                append("- ${role.description}\n")
            }
        }
    }
}

@Composable
fun LinkButton(icon: Painter, text: String, url: String) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(mShapes.small)
            .clickable {
                context.startActivity(
                    Intent(Intent.ACTION_VIEW, url.toUri())
                )
            }
            .border(1.dp, mColors.tertiaryContainer, mShapes.small)
            .padding(8.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = Color.Unspecified
        )
        Text(text = text, style = mTypography.bodyLarge)
    }
}

@Preview
@Composable
fun LinkButtonPreview() {
    LibriaNowTheme {
        LinkButton(
            icon = painterResource(LibriaNowIcons.TelegramMulticolored),
            text = "Telegram",
            url = ""
        )
    }
}

@Preview
@Composable
fun JoinTeamBSPreview() {
    LibriaNowTheme {
        JoinTeamBS(
            onDismissRequest = {}
        )
    }
}