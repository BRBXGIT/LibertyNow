package com.example.data.data

import com.example.data.domain.ThemeRepo
import com.example.local.datastore.app_theme.AppThemeManager
import com.example.local.datastore.app_theme.ThemeState
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemeRepoImpl @Inject constructor(
    private val appThemeManager: AppThemeManager
): ThemeRepo {

    override val theme = appThemeManager.theme

    override val colorSystem = appThemeManager.colorSystem

    override val themeState = theme.map { theme ->
        when(theme) {
            null -> ThemeState.Loading
            else -> ThemeState.Loaded
        }
    }

    override val colorSystemState = colorSystem.map { colorSystem ->
        when(colorSystem) {
            null -> ThemeState.Loading
            else -> ThemeState.Loaded
        }
    }

    override suspend fun saveTheme(theme: String) {
        appThemeManager.saveTheme(theme)
    }

    override suspend fun saveColorSystem(colorSystem: String) {
        appThemeManager.saveColorSystem(colorSystem)
    }
}