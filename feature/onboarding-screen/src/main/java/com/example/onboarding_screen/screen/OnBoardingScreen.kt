package com.example.onboarding_screen.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.onboarding_screen.sections.AboutAppSection
import com.example.onboarding_screen.sections.ScreenHeader
import com.example.onboarding_screen.sections.StartButton

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingScreenVM
) {
    val darkTheme = isSystemInDarkTheme()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(
                        if (darkTheme) LibriaNowIcons.OnBoardingBackgroundDark else LibriaNowIcons.OnBoardingBackgroundLight
                    ),
                    contentScale = ContentScale.Crop
                )
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = CommonConstants.HORIZONTAL_PADDING.dp,
                    end = CommonConstants.HORIZONTAL_PADDING.dp
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