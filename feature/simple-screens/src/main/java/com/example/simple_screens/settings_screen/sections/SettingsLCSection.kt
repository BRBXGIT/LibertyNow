package com.example.simple_screens.settings_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

enum class PlayerSettingsItemType {
    VideoQuality, ShowSkipOpeningButton, Crop, AutoPlay
}

data class PlayerSettingsItem(
    val name: String,
    val label: String,
    val type: PlayerSettingsItemType,
    val isActive: Boolean,
    val icon: Int
)

@Composable
fun SettingsLCSection(
    chosenTheme: String,
    chosenColorSystem: String,
    videoQuality: Int,
    autoPlay: Boolean,
    showSkipOpeningButton: Boolean,
    isCropped: Boolean,
    onThemeChange: (String) -> Unit,
    onColorSystemChange: (String) -> Unit,
    onCheckChange: (PlayerSettingsItemType) -> Unit,
) {
    val playerSettingsItems = listOf(
        PlayerSettingsItem(
            name = "Качество",
            label = videoQuality.toString(),
            type = PlayerSettingsItemType.VideoQuality,
            isActive = true, //Doesn't matter what value here :)
            icon = LibriaNowIcons.Quality
        ),
        PlayerSettingsItem(
            name = "Кнопка пропуска",
            label = "Показывать кнопку пропуска опенинга",
            type = PlayerSettingsItemType.ShowSkipOpeningButton,
            isActive = showSkipOpeningButton == true,
            icon = LibriaNowIcons.RewindCircle
        ),
        PlayerSettingsItem(
            name = "Автовоспроизведение",
            label = "Автоматически воспроизводить следующий эпизод",
            type = PlayerSettingsItemType.AutoPlay,
            isActive = autoPlay == true,
            icon = LibriaNowIcons.AutoPlay
        ),
        PlayerSettingsItem(
            name = "Полный экран",
            label = "Воспроизведение на полный экран",
            type = PlayerSettingsItemType.Crop,
            isActive = isCropped == true,
            icon = LibriaNowIcons.Crop
        )
    )

    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Тема",
                style = mTypography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ThemePreviewsSection(
                    chosenTheme = chosenTheme,
                    onThemeClick = { onThemeChange(it) }
                )

                ColorSystemElements(
                    chosenTheme = chosenTheme,
                    onColorSystemClick = { onColorSystemChange(it) },
                    chosenColorSystem = chosenColorSystem
                )
            }
        }

        item {
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        }

        item {
            Text(
                text = "Плеер",
                style = mTypography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        items(playerSettingsItems) { playerItem ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(mShapes.small)
                    .background(mColors.surfaceContainerHigh)
                    .clickable {
                        onCheckChange(playerItem.type)
                    }
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(playerItem.icon),
                    contentDescription = null
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = playerItem.name,
                        style = mTypography.bodyLarge
                    )

                    Text(
                        text = playerItem.label,
                        style = mTypography.bodyMedium.copy(
                            color = mColors.secondary
                        )
                    )
                }

                if(playerItem.type != PlayerSettingsItemType.VideoQuality) {
                    Switch(
                        checked = playerItem.isActive,
                        onCheckedChange = { onCheckChange(playerItem.type) },
                    )
                }
            }
        }
    }
}