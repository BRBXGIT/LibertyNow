package com.example.navbar_screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import com.example.anime_screen.navigation.AnimeScreenRoute
import com.example.common.auth.AuthIntent
import com.example.common.auth.AuthState
import com.example.common.auth.AuthVM
import com.example.common.common.CommonState
import com.example.data.domain.AuthRepo
import com.example.data.domain.LikesRepo
import com.example.design_system.sections.auth_bs.AuthBSConstants
import com.example.design_system.theme.LibriaNowTheme
import com.example.local.datastore.auth.LoggingState
import com.example.navbar_screens.likes_screen.screen.LikesScreen
import com.example.navbar_screens.likes_screen.screen.LikesScreenState
import com.example.navbar_screens.likes_screen.sections.LoggedInSection
import com.example.navbar_screens.likes_screen.sections.LoggedOutSection
import com.example.navbar_screens.likes_screen.sections.LoggedOutSectionConstants
import com.example.network.common.models.anime_list_with_pagination_response.Data
import com.example.network.common.models.common.Name
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LikesScreenTest {

    private lateinit var authVM: AuthVM

    private val navController: NavController = mockk(relaxed = true)
    private val authRepo: AuthRepo = mockk(relaxed = true)
    private val likesRepo: LikesRepo = mockk(relaxed = true)
    private val dispatcher = StandardTestDispatcher()

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        coEvery { authRepo.loggingState } returns flowOf(LoggingState.LoggedOut)
        coEvery { authRepo.userSessionToken } returns flowOf(null)

        authVM = AuthVM(authRepo, likesRepo, dispatcher)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun authBS_visible_when_state_true() {
        composeTestRule.setContent {
            LibriaNowTheme {
                LikesScreen(
                    authState = AuthState(isAuthBSOpened = true),
                    commonState = CommonState(),
                    screenState = LikesScreenState(),
                    navController = navController,
                    useExpressive = false,
                    onIntent = {},
                    onAuthIntent = {},
                    onCommonIntent = {}
                )
            }
        }

        authVM.sendIntent(AuthIntent.ChangeIsAuthBsOpened)

        dispatcher.scheduler.advanceUntilIdle()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(AuthBSConstants.AUTH_BS_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun loggedOutSection_shows_text_and_calls_onClick() {
        var clicked = 0

        composeTestRule.setContent {
            LibriaNowTheme {
                LoggedOutSection(
                    onAuthClick = { clicked++ }
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(LoggedOutSectionConstants.WHY_NEED_AUTH_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LoggedOutSectionConstants.AUTH_BUTTON_TEST_TAG).performClick()

        assertEquals(1, clicked)
    }

    @Test
    fun loggedInSection_filtersLikes_whenSearching() {
        val likes = listOf(
            Data(
                id = 0,
                name = Name(main = "Наруто")
            ),
            Data(
                id = 1,
                name = Name(main = "Ван пис")
            )
        )

        val screenState = LikesScreenState(
            isSearching = true,
            query = "Наруто"
        )
        val authState = AuthState(likes = likes)

        composeTestRule.setContent {
            LibriaNowTheme {
                LoggedInSection(
                    screenState = screenState,
                    authState = authState,
                    navController = navController
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Наруто").assertExists()
        composeTestRule.onNodeWithText("Ван пис").assertDoesNotExist()
    }

    @Test
    fun loggedInSection_clickNavigatesToAnimeScreen() {
        val likes = listOf(
            Data(
                id = 1,
                name = Name(main = "Блич")
            )
        )

        val screenState = LikesScreenState(isSearching = false, query = "")
        val authState = AuthState(likes = likes)

        composeTestRule.setContent {
            LibriaNowTheme {
                LoggedInSection(
                    screenState = screenState,
                    authState = authState,
                    navController = navController
                )
            }
        }

        composeTestRule.onNodeWithText("Блич").performClick()

        verify { navController.navigate(AnimeScreenRoute(1)) }
    }
}