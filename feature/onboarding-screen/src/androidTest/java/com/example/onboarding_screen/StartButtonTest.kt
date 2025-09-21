package com.example.onboarding_screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.design_system.theme.LibriaNowTheme
import com.example.onboarding_screen.sections.StartButton
import com.example.onboarding_screen.sections.StartButtonConstants
import org.junit.Rule
import org.junit.Test

class StartButtonTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun startButton_displays_text_and_call_onClick() {
        var clicked = 0

        composeTestRule.setContent {
            LibriaNowTheme {
                StartButton(
                    onClick = { clicked++ }
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(StartButtonConstants.START_BUTTON_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(StartButtonConstants.START_BUTTON_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(StartButtonConstants.START_BUTTON_TEST_TAG).performClick()
    }
}