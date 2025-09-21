package com.example.onboarding_screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.design_system.theme.LibriaNowTheme
import com.example.onboarding_screen.sections.AboutAppSection
import com.example.onboarding_screen.sections.AboutAppSectionConstants
import org.junit.Rule
import org.junit.Test

class AboutAppSectionTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun aboutAppSection_displays_text() {
        composeTestRule.setContent {
            LibriaNowTheme {
                AboutAppSection()
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(AboutAppSectionConstants.GREETING_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithText(AboutAppSectionConstants.ABOUT_APP_TEXT).assertIsDisplayed()
    }
}