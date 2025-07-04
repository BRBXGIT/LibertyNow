package com.example.design_system.theme

import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun LibriaNowTheme(
    colorSystem: String = "default",
    theme: String = "default",
    darkTheme: Boolean = isSystemInDarkTheme(),
    context: Context = LocalContext.current,
    content: @Composable () -> Unit
) {
    val colorScheme = when(theme) {
        "default" -> if(darkTheme) {
            when(colorSystem) {
                "default" -> DarkColorScheme
                "dark" -> DarkColorScheme
                "light" -> DarkColorScheme
                "lightGreenApple" -> DarkGreenAppleScheme
                "darkGreenApple" -> DarkGreenAppleScheme
                "lightSakura" -> DarkSakuraScheme
                "darkSakura" -> DarkSakuraScheme
                "lightTacos" -> DarkTacosScheme
                "darkTacos" -> DarkTacosScheme
                "lightLavender" -> DarkLavenderScheme
                "darkLavender" -> DarkLavenderScheme
                "lightSea" -> LightSeaScheme
                "darkSea" -> DarkSeaScheme
                else -> DarkColorScheme
            }
        } else {
            when(colorSystem) {
                "default" -> LightColorScheme
                "dark" -> LightColorScheme
                "light" -> LightColorScheme
                "lightGreenApple" -> LightGreenAppleScheme
                "darkGreenApple" -> LightGreenAppleScheme
                "lightSakura" -> LightSakuraScheme
                "darkSakura" -> LightSakuraScheme
                "lightTacos" -> LightTacosScheme
                "darkTacos" -> LightTacosScheme
                "lightLavender" -> LightLavenderScheme
                "darkLavender" -> LightLavenderScheme
                "lightSea" -> LightSeaScheme
                "darkSea" -> LightSeaScheme
                else -> LightColorScheme
            }
        }
        "dark" -> when(colorSystem) {
            "default" -> DarkColorScheme
            "dark" -> DarkColorScheme
            "light" -> DarkColorScheme
            "lightGreenApple" -> DarkGreenAppleScheme
            "darkGreenApple" -> DarkGreenAppleScheme
            "lightSakura" -> DarkSakuraScheme
            "darkSakura" -> DarkSakuraScheme
            "lightTacos" -> DarkTacosScheme
            "darkTacos" -> DarkTacosScheme
            "lightLavender" -> DarkLavenderScheme
            "darkLavender" -> DarkLavenderScheme
            "lightSea" -> DarkSeaScheme
            "darkSea" -> DarkSeaScheme
            else -> DarkColorScheme
        }
        "light" -> when(colorSystem) {
            "default" -> LightColorScheme
            "dark" -> LightColorScheme
            "light" -> LightColorScheme
            "lightGreenApple" -> LightGreenAppleScheme
            "darkGreenApple" -> LightGreenAppleScheme
            "lightSakura" -> LightSakuraScheme
            "darkSakura" -> LightSakuraScheme
            "lightTacos" -> LightTacosScheme
            "darkTacos" -> LightTacosScheme
            "lightLavender" -> LightLavenderScheme
            "darkLavender" -> LightLavenderScheme
            "lightSea" -> LightSeaScheme
            "darkSea" -> LightSeaScheme
            else -> LightColorScheme
        }
        else -> if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if(darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        } else {
            if(darkTheme) {
                DarkColorScheme
            } else {
                LightColorScheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

val mColors @Composable get() = MaterialTheme.colorScheme
val mTypography @Composable get() = MaterialTheme.typography
val mShapes @Composable get() = MaterialTheme.shapes