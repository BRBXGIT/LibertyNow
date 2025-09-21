package com.example.onboarding_screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.design_system.theme.LibriaNowTheme
import com.example.onboarding_screen.sections.ScreenHeader
import com.example.onboarding_screen.sections.ScreenHeaderConstants
import org.junit.Rule
import org.junit.Test

class ScreenHeaderTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun screenHeader_displays_text() {
        composeTestRule.setContent {
            LibriaNowTheme {
                ScreenHeader()
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(ScreenHeaderConstants.TEXT_TEST_TAG).assertIsDisplayed()
    }
}