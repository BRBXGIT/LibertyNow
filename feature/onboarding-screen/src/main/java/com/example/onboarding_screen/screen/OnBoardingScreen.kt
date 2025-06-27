package com.example.onboarding_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design_system.theme.CommonDimens
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import com.example.onboarding_screen.sections.AboutAppSection
import com.example.onboarding_screen.sections.StartButton
import com.example.onboarding_screen.sections.ScreenHeader

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingScreenVM
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            mColors.primaryContainer,
                            mColors.secondary
                        )
                    )
                )
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = CommonDimens.HORIZONTAL_PADDING.dp,
                    end = CommonDimens.HORIZONTAL_PADDING.dp
                )
        ) {
            ScreenHeader()

            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                AboutAppSection()

                StartButton(
                    onClick = {
                        viewModel.sendIntent(
                            OnBoardingScreenIntent.SaveIsOnBoardingCompleted(true)
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun OnBoardingScreenPreview() {
    LibriaNowTheme {
        val onBoardingScreenVM = hiltViewModel<OnBoardingScreenVM>()

        OnBoardingScreen(onBoardingScreenVM)
    }
}