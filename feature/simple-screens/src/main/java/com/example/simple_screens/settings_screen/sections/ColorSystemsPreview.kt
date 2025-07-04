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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
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
    val name: String,
    val previewName: String,
    val tertiary: Color,
)

@Composable
fun ColorSystemElements(
    chosenTheme: String,
    chosenColorSystem: String,
    onColorSystemClick: (String) -> Unit
) {
    val darkColorSystems = listOf(
        ColorSystemPreviewColors(
            secondaryContainer = DarkColorScheme.secondaryContainer,
            background = DarkColorScheme.background,
            surfaceContainerHighest = DarkColorScheme.surfaceContainerHighest,
            secondary = DarkColorScheme.secondary,
            onSecondaryContainer = DarkColorScheme.onSecondaryContainer,
            primary = DarkColorScheme.primary,
            name = "darkScheme",
            previewName = "Default",
            tertiary = DarkColorScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = darkGreenAppleScheme.secondaryContainer,
            background = darkGreenAppleScheme.background,
            surfaceContainerHighest = darkGreenAppleScheme.surfaceContainerHighest,
            secondary = darkGreenAppleScheme.secondary,
            onSecondaryContainer = darkGreenAppleScheme.onSecondaryContainer,
            primary = darkGreenAppleScheme.primary,
            name = "darkGreenApple",
            previewName = "Green apple",
            tertiary = darkGreenAppleScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = darkSakuraScheme.secondaryContainer,
            background = darkSakuraScheme.background,
            surfaceContainerHighest = darkSakuraScheme.surfaceContainerHighest,
            secondary = darkSakuraScheme.secondary,
            onSecondaryContainer = darkSakuraScheme.onSecondaryContainer,
            primary = darkSakuraScheme.primary,
            name = "darkSakura",
            previewName = "Sakura",
            tertiary = darkSakuraScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = darkTacosScheme.secondaryContainer,
            background = darkTacosScheme.background,
            surfaceContainerHighest = darkTacosScheme.surfaceContainerHighest,
            secondary = darkTacosScheme.secondary,
            onSecondaryContainer = darkTacosScheme.onSecondaryContainer,
            primary = darkTacosScheme.primary,
            name = "darkTacos",
            previewName = "Tacos",
            tertiary = darkTacosScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = darkLavenderScheme.secondaryContainer,
            background = darkLavenderScheme.background,
            surfaceContainerHighest = darkLavenderScheme.surfaceContainerHighest,
            secondary = darkLavenderScheme.secondary,
            onSecondaryContainer = darkLavenderScheme.onSecondaryContainer,
            primary = darkLavenderScheme.primary,
            name = "darkLavender",
            previewName = "Lavender",
            tertiary = darkLavenderScheme.tertiary
        ),
    )

    val lightColorSystems = listOf(
        ColorSystemPreviewColors(
            secondaryContainer = LightColorScheme.secondaryContainer,
            background = LightColorScheme.background,
            surfaceContainerHighest = LightColorScheme.surfaceContainerHighest,
            secondary = LightColorScheme.secondary,
            onSecondaryContainer = LightColorScheme.onSecondaryContainer,
            primary = LightColorScheme.primary,
            name = "light",
            previewName = "Default",
            tertiary = LightColorScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = lightGreenAppleScheme.secondaryContainer,
            background = lightGreenAppleScheme.background,
            surfaceContainerHighest = lightGreenAppleScheme.surfaceContainerHighest,
            secondary = lightGreenAppleScheme.secondary,
            onSecondaryContainer = lightGreenAppleScheme.onSecondaryContainer,
            primary = lightGreenAppleScheme.primary,
            name = "lightGreenApple",
            previewName = "Green apple",
            tertiary = lightGreenAppleScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = lightSakuraScheme.secondaryContainer,
            background = lightSakuraScheme.background,
            surfaceContainerHighest = lightSakuraScheme.surfaceContainerHighest,
            secondary = lightSakuraScheme.secondary,
            onSecondaryContainer = lightSakuraScheme.onSecondaryContainer,
            primary = lightSakuraScheme.primary,
            name = "lightSakura",
            previewName = "Sakura",
            tertiary = lightSakuraScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = lightTacosScheme.secondaryContainer,
            background = lightTacosScheme.background,
            surfaceContainerHighest = lightTacosScheme.surfaceContainerHighest,
            secondary = lightTacosScheme.secondary,
            onSecondaryContainer = lightTacosScheme.onSecondaryContainer,
            primary = lightTacosScheme.primary,
            name = "lightTacos",
            previewName = "Tacos",
            tertiary = lightTacosScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = lightLavenderScheme.secondaryContainer,
            background = lightLavenderScheme.background,
            surfaceContainerHighest = lightLavenderScheme.surfaceContainerHighest,
            secondary = lightLavenderScheme.secondary,
            onSecondaryContainer = lightLavenderScheme.onSecondaryContainer,
            primary = lightLavenderScheme.primary,
            name = "lightLavender",
            previewName = "Lavender",
            tertiary = lightLavenderScheme.tertiary
        ),
    )

    val darkThemeBySystem = isSystemInDarkTheme()
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            horizontal = 16.dp
        )
    ) {
        when(chosenTheme) {
            "default" -> if(darkThemeBySystem) {
                items(darkColorSystems) { colorSystem ->
                    ColorSystemPreview(
                        tertiary = colorSystem.tertiary,
                        secondaryContainer = colorSystem.secondaryContainer,
                        background = colorSystem.background,
                        surfaceContainerHighest = colorSystem.surfaceContainerHighest,
                        secondary = colorSystem.secondary,
                        onSecondaryContainer = colorSystem.onSecondaryContainer,
                        primary = colorSystem.primary,
                        chosenColorSystem = chosenColorSystem,
                        name = colorSystem.name,
                        previewName = colorSystem.previewName,
                        onClick = { onColorSystemClick(colorSystem.name) }
                    )
                }
            } else {
                items(lightColorSystems) { colorSystem ->
                    ColorSystemPreview(
                        tertiary = colorSystem.tertiary,
                        secondaryContainer = colorSystem.secondaryContainer,
                        background = colorSystem.background,
                        surfaceContainerHighest = colorSystem.surfaceContainerHighest,
                        secondary = colorSystem.secondary,
                        onSecondaryContainer = colorSystem.onSecondaryContainer,
                        primary = colorSystem.primary,
                        chosenColorSystem = chosenColorSystem,
                        name = colorSystem.name,
                        previewName = colorSystem.previewName,
                        onClick = { onColorSystemClick(colorSystem.name) }
                    )
                }
            }
            "light" -> {
                items(lightColorSystems) { colorSystem ->
                    ColorSystemPreview(
                        tertiary = colorSystem.tertiary,
                        secondaryContainer = colorSystem.secondaryContainer,
                        background = colorSystem.background,
                        surfaceContainerHighest = colorSystem.surfaceContainerHighest,
                        secondary = colorSystem.secondary,
                        onSecondaryContainer = colorSystem.onSecondaryContainer,
                        primary = colorSystem.primary,
                        chosenColorSystem = chosenColorSystem,
                        name = colorSystem.name,
                        previewName = colorSystem.previewName,
                        onClick = { onColorSystemClick(colorSystem.name) }
                    )
                }
            }
            "dark" -> {
                items(darkColorSystems) { colorSystem ->
                    ColorSystemPreview(
                        tertiary = colorSystem.tertiary,
                        secondaryContainer = colorSystem.secondaryContainer,
                        background = colorSystem.background,
                        surfaceContainerHighest = colorSystem.surfaceContainerHighest,
                        secondary = colorSystem.secondary,
                        onSecondaryContainer = colorSystem.onSecondaryContainer,
                        primary = colorSystem.primary,
                        chosenColorSystem = chosenColorSystem,
                        name = colorSystem.name,
                        previewName = colorSystem.previewName,
                        onClick = { onColorSystemClick(colorSystem.name) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ColorSystemPreview(
    tertiary: Color,
    secondaryContainer: Color,
    background: Color,
    surfaceContainerHighest: Color,
    secondary: Color,
    onSecondaryContainer: Color,
    primary: Color,
    chosenColorSystem: String,
    name: String,
    previewName: String,
    onClick: () -> Unit
) {
    val chosen = chosenColorSystem == name

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(110.dp)
                .height(180.dp)
                .background(
                    shape = mShapes.small,
                    color = background
                )
                .border(
                    width = if(chosen) 2.dp else 1.dp,
                    color = if(chosen) mColors.primary else mColors.secondary,
                    shape = mShapes.small
                )
                .clip(mShapes.small)
                .clickable {
                    onClick()
                }
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(
                        color = surfaceContainerHighest,
                        shape = mShapes.small.copy(
                            bottomEnd = CornerSize(0.dp),
                            bottomStart = CornerSize(0.dp)
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
                            .size(1.dp, 60.dp)
                            .background(
                                color = surfaceContainerHighest,
                                shape = mShapes.extraSmall
                            )
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .height(15.dp)
                                .background(
                                    color = secondaryContainer,
                                    shape = mShapes.extraSmall.copy(
                                        topEnd = CornerSize(0.dp),
                                        topStart = CornerSize(0.dp),
                                    )
                                )
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(start = 4.dp, top = 2.dp)
                                    .size(30.dp, 3.dp)
                                    .background(
                                        color = onSecondaryContainer,
                                        shape = mShapes.extraSmall
                                    )
                            )

                            Box(
                                modifier = Modifier
                                    .padding(start = 4.dp, bottom = 2.dp)
                                    .size(20.dp, 3.dp)
                                    .background(
                                        color = secondary,
                                        shape = mShapes.extraSmall
                                    )
                            )
                        }
                    }
                }
            }

            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(
                        color = surfaceContainerHighest,
                        shape = mShapes.small.copy(
                            topEnd = CornerSize(0.dp),
                            topStart = CornerSize(0.dp)
                        )
                    )
                    .padding(end = 4.dp)
            ) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    contentPadding = PaddingValues(
                        horizontal = 4.dp
                    )
                ) {
                    items(4) {
                        Box(
                            modifier = Modifier
                                .size(
                                    height = 8.dp,
                                    width = 12.dp
                                )
                                .background(
                                    shape = CircleShape,
                                    color = secondaryContainer
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(2.dp)
                                    .background(
                                        color = onSecondaryContainer,
                                        shape = CircleShape
                                    ),
                            )
                        }
                    }
                }
            }
        }

        Text(
            text = previewName,
            style = mTypography.labelMedium
        )
    }
}