package com.example.librianow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.theme.LibriaNowTheme
import com.example.local.datastore.onboarding.OnBoardingState
import com.example.navbar_screens.home_screen.navigation.HomeScreenRoute
import com.example.onboarding_screen.navigation.OnBoardingScreenRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Avoid bug when theme doesn't want to change after splashscreen
//        setTheme(R.style.Theme_LibriaNow)

        enableEdgeToEdge()
        setContent {
            val appStartingVM = hiltViewModel<AppStartingVM>()
            val appStartingState by appStartingVM.appStartingState.collectAsStateWithLifecycle()

            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition {
//                (appStartingState.onBoardingState is OnBoardingState.Loading) and
//                        (appStartingState.themeState is ThemeState.Loaded) and
//                        (appStartingState.colorSystemState is ThemeState.Loaded)
                true
            }

            LibriaNowTheme(
                colorSystem = appStartingState.colorSystem,
                theme = appStartingState.theme,
            ) {
                if (appStartingState.onBoardingState !is OnBoardingState.Loading) {
                    NavGraph(
                        startDestination = if (appStartingState.onBoardingState is OnBoardingState.Completed) {
                            HomeScreenRoute
                        } else {
                            OnBoardingScreenRoute
                        }
                    )
                }
            }
        }
    }
}