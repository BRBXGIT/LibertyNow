package com.example.navbar_screens

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.common.auth.AuthState
import com.example.common.auth.AuthVM
import com.example.common.common.CommonState
import com.example.common.common.CommonVM
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.navbar_screens.more_screen.screen.MoreScreen
import com.example.navbar_screens.more_screen.screen.MoreScreenState
import com.example.navbar_screens.more_screen.screen.MoreScreenVM
import com.example.navbar_screens.more_screen.sections.MoreItemUi
import com.example.navbar_screens.more_screen.sections.MoreLCConstants
import com.example.navbar_screens.more_screen.sections.QuitAccountADConstants
import com.example.simple_screens.settings_screen.navigation.SettingsScreenRoute
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoreScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val commonVM: CommonVM = mockk(relaxed = true)
    private val vm: MoreScreenVM = mockk(relaxed = true)
    private val authVM: AuthVM = mockk(relaxed = true)
    private val navController: NavController = mockk(relaxed = true)

    @Before
    fun setUp() {
        every { commonVM.commonState } returns MutableStateFlow(CommonState())
        every { vm.moreScreenState } returns MutableStateFlow(MoreScreenState())
        every { authVM.authState } returns MutableStateFlow(AuthState())
    }

    @Test
    fun quitAdVisible_when_state_is_true() {
        val quitAdVisibleState = MoreScreenState(isQuitADVisible = true)
        every { vm.moreScreenState } returns MutableStateFlow(quitAdVisibleState)

        composeTestRule.setContent {
            val moreScreenState by vm.moreScreenState.collectAsStateWithLifecycle()

            LibriaNowTheme {
                MoreScreen(
                    commonState = CommonState(),
                    screenState = moreScreenState,
                    authState = AuthState(),
                    onCommonIntent = {},
                    onAuthIntent = {},
                    onIntent = {},
                    navController = navController
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(QuitAccountADConstants.AD_TEST_TAG)
    }

    @Test
    fun moreLC_visible() {
        composeTestRule.setContent {
            LibriaNowTheme {
                MoreScreen(
                    commonState = CommonState(),
                    screenState = MoreScreenState(),
                    authState = AuthState(),
                    onCommonIntent = {},
                    onAuthIntent = {},
                    onIntent = {},
                    navController = navController
                )
            }
        }

        composeTestRule.onNodeWithTag(MoreLCConstants.LC_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun moreItemUi_calls_onClick() {
        var clicked = 1

        composeTestRule.setContent {
            LibriaNowTheme {
                MoreItemUi(
                    onClick = { clicked++ },
                    icon = LibriaNowIcons.Settings,
                    label = "Label",
                    fromLink = false
                )
            }
        }

        composeTestRule.onNodeWithText("Label").performClick()

        composeTestRule.waitForIdle()

        assertEquals(2, clicked)
    }

    @Test
    fun moreItemUi_calls_navigate_method() {
        coEvery { navController.navigate(SettingsScreenRoute) } just Runs

        composeTestRule.setContent {
            LibriaNowTheme {
                MoreScreen(
                    commonState = CommonState(),
                    screenState = MoreScreenState(),
                    authState = AuthState(),
                    onCommonIntent = {},
                    onAuthIntent = {},
                    onIntent = {},
                    navController = navController
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Настройки").performClick()

        verify { navController.navigate(SettingsScreenRoute) }
    }
}