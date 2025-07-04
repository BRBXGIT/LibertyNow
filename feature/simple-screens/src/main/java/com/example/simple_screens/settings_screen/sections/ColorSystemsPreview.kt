package com.example.simple_screens.settings_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.DarkColorScheme
import com.example.design_system.theme.LightColorScheme
import com.example.design_system.theme.darkGreenAppleScheme
import com.example.design_system.theme.darkLavenderScheme
import com.example.design_system.theme.darkSakuraScheme
import com.example.design_system.theme.darkTacosScheme
import com.example.design_system.theme.lightGreenAppleScheme
import com.example.design_system.theme.lightLavenderScheme
import com.example.design_system.theme.lightSakuraScheme
import com.example.design_system.theme.lightTacosScheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

data class ColorSystemPreviewColors(
    val secondaryContainer: Color,
    val background: Color,
    val surfaceContainerHighest: Color,
    val secondary: Color,
    val onSecondaryContainer: Color,
    val primary: Color,
    val tertiary: Color,
    val name: String,
    val previewName: String,
)

fun colorSystemFromScheme(
    scheme: ColorScheme,
    name: String,
    previewName: String
): ColorSystemPreviewColors {
    return ColorSystemPreviewColors(
        secondaryContainer = scheme.secondaryContainer,
        background = scheme.background,
        surfaceContainerHighest = scheme.surfaceContainerHighest,
        secondary = scheme.secondary,
        onSecondaryContainer = scheme.onSecondaryContainer,
        primary = scheme.primary,
        tertiary = scheme.tertiary,
        name = name,
        previewName = previewName
    )
}

@Composable
fun ColorSystemElements(
    chosenTheme: String,
    chosenColorSystem: String,
    onColorSystemClick: (String) -> Unit
) {
    val darkColorSystems = listOf(
        colorSystemFromScheme(DarkColorScheme, "darkScheme", "AniLiberty"),
        colorSystemFromScheme(darkGreenAppleScheme, "darkGreenApple", "Яблоко"),
        colorSystemFromScheme(darkSakuraScheme, "darkSakura", "Сакура"),
        colorSystemFromScheme(darkTacosScheme, "darkTacos", "Тако"),
        colorSystemFromScheme(darkLavenderScheme, "darkLavender", "Лаванда")
    )

    val lightColorSystems = listOf(
        colorSystemFromScheme(LightColorScheme, "light", "AniLiberty"),
        colorSystemFromScheme(lightGreenAppleScheme, "lightGreenApple", "Яблоко"),
        colorSystemFromScheme(lightSakuraScheme, "lightSakura", "Сакура"),
        colorSystemFromScheme(lightTacosScheme, "lightTacos", "Тако"),
        colorSystemFromScheme(lightLavenderScheme, "lightLavender", "Лаванда")
    )

    val isDark = isSystemInDarkTheme()
    val colorSystems = when (chosenTheme) {
        "dark" -> darkColorSystems
        "light" -> lightColorSystems
        "default" -> if (isDark) darkColorSystems else lightColorSystems
        else -> lightColorSystems
    }

    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(colorSystems) { colorSystem ->
            ColorSystemPreview(
                colorSystem = colorSystem,
                isChosen = colorSystem.name == chosenColorSystem,
                onClick = { onColorSystemClick(colorSystem.name) }
            )
        }
    }
}

@Composable
private fun ColorSystemPreview(
    colorSystem: ColorSystemPreviewColors,
    isChosen: Boolean,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(width = 110.dp, height = 180.dp)
                .background(color = colorSystem.background, shape = mShapes.small)
                .border(
                    width = if (isChosen) 2.dp else 1.dp,
                    color = if (isChosen) mColors.primary else mColors.secondary,
                    shape = mShapes.small
                )
                .clip(mShapes.small)
                .clickable { onClick() }
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(
                        color = colorSystem.surfaceContainerHighest,
                        shape = mShapes.small.copy(
                            bottomStart = CornerSize(0.dp),
                            bottomEnd = CornerSize(0.dp)
                        )
                    )
            )

            LazyVerticalGrid(
                modifier = Modifier.padding(top = 20.dp),
                contentPadding = PaddingValues(8.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(3) {
                    Box(
                        modifier = Modifier
                            .size(width = 1.dp, height = 60.dp)
                            .background(
                                color = colorSystem.surfaceContainerHighest,
                                shape = mShapes.extraSmall
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .height(15.dp)
                                .background(
                                    color = colorSystem.secondaryContainer,
                                    shape = mShapes.extraSmall.copy(
                                        topStart = CornerSize(0.dp),
                                        topEnd = CornerSize(0.dp)
                                    )
                                ),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(start = 4.dp, top = 2.dp)
                                    .size(width = 30.dp, height = 3.dp)
                                    .background(
                                        colorSystem.onSecondaryContainer,
                                        shape = mShapes.extraSmall
                                    )
                            )
                            Box(
                                modifier = Modifier
                                    .padding(start = 4.dp, bottom = 2.dp)
                                    .size(width = 20.dp, height = 3.dp)
                                    .background(colorSystem.secondary, shape = mShapes.extraSmall)
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(
                        color = colorSystem.surfaceContainerHighest,
                        shape = mShapes.small.copy(
                            topStart = CornerSize(0.dp),
                            topEnd = CornerSize(0.dp)
                        )
                    )
                    .padding(horizontal = 4.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    items(4) {
                        Box(
                            modifier = Modifier
                                .size(width = 12.dp, height = 8.dp)
                                .background(colorSystem.secondaryContainer, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(2.dp)
                                    .background(
                                        colorSystem.onSecondaryContainer,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                }
            }
        }

        Text(
            text = colorSystem.previewName,
            style = mTypography.labelMedium
        )
    }
}