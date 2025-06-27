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
        enableEdgeToEdge()
        setContent {
            val appStartingVM = hiltViewModel<AppStartingVM>()
            val onBoardingState by appStartingVM.onBoardingState.collectAsStateWithLifecycle()

            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition {
                onBoardingState is OnBoardingState.Loading
            }

            LibriaNowTheme {
                if (onBoardingState !is OnBoardingState.Loading) {
                    NavGraph(
                        startDestination = if (onBoardingState is OnBoardingState.Completed) {
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