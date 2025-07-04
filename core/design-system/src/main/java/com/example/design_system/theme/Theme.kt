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
                "lightGreenApple" -> darkGreenAppleScheme
                "darkGreenApple" -> darkGreenAppleScheme
                "lightSakura" -> darkSakuraScheme
                "darkSakura" -> darkSakuraScheme
                "lightTacos" -> darkTacosScheme
                "darkTacos" -> darkTacosScheme
                "lightLavender" -> darkLavenderScheme
                "darkLavender" -> darkLavenderScheme
                else -> DarkColorScheme
            }
        } else {
            when(colorSystem) {
                "default" -> LightColorScheme
                "dark" -> LightColorScheme
                "light" -> LightColorScheme
                "lightGreenApple" -> lightGreenAppleScheme
                "darkGreenApple" -> lightGreenAppleScheme
                "lightSakura" -> lightSakuraScheme
                "darkSakura" -> lightSakuraScheme
                "lightTacos" -> lightTacosScheme
                "darkTacos" -> lightTacosScheme
                "lightLavender" -> lightLavenderScheme
                "darkLavender" -> lightLavenderScheme
                else -> LightColorScheme
            }
        }
        "dark" -> when(colorSystem) {
            "default" -> DarkColorScheme
            "dark" -> DarkColorScheme
            "light" -> DarkColorScheme
            "lightGreenApple" -> darkGreenAppleScheme
            "darkGreenApple" -> darkGreenAppleScheme
            "lightSakura" -> darkSakuraScheme
            "darkSakura" -> darkSakuraScheme
            "lightTacos" -> darkTacosScheme
            "darkTacos" -> darkTacosScheme
            "lightLavender" -> darkLavenderScheme
            "darkLavender" -> darkLavenderScheme
            else -> DarkColorScheme
        }
        "light" -> when(colorSystem) {
            "default" -> LightColorScheme
            "dark" -> LightColorScheme
            "light" -> LightColorScheme
            "lightGreenApple" -> lightGreenAppleScheme
            "darkGreenApple" -> lightGreenAppleScheme
            "lightSakura" -> lightSakuraScheme
            "darkSakura" -> lightSakuraScheme
            "lightTacos" -> lightTacosScheme
            "darkTacos" -> lightTacosScheme
            "lightLavender" -> lightLavenderScheme
            "darkLavender" -> lightLavenderScheme
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