package com.example.navbar_screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.design_system.cards.AnimeCardConstants
import com.example.design_system.sections.error_section.ErrorSectionConstants
import com.example.design_system.theme.LibriaNowTheme
import com.example.navbar_screens.common.AnimeLVGContainerConstants
import com.example.navbar_screens.home_screen.screen.HomeScreenState
import com.example.navbar_screens.home_screen.screen.HomeScreenVM
import com.example.navbar_screens.home_screen.sections.AnimeUpdatesContent
import com.example.navbar_screens.home_screen.sections.NothingHereSection
import com.example.navbar_screens.home_screen.sections.NothingHereSectionConstants
import com.example.navbar_screens.home_screen.sections.RandomAnimeButton
import com.example.navbar_screens.home_screen.sections.RandomAnimeButtonConstants
import com.example.navbar_screens.home_screen.sections.SearchingContent
import com.example.network.common.models.anime_list_with_pagination_response.Data
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    private val vm: HomeScreenVM = mockk(relaxed = true)
    private val navController: NavController = mockk(relaxed = true)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun randomAnimeButton_displayed_and_call_onClick() {
        var clicked = 0

        composeTestRule.setContent {
            LibriaNowTheme {
                RandomAnimeButton(
                    onClick = { clicked++ }
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(RandomAnimeButtonConstants.BUTTON_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(RandomAnimeButtonConstants.BUTTON_TEST_TAG).performClick()

        assertEquals(1, clicked)
    }

    @Test
    fun animeUpdatesContent_displays_errorSection_if_error() {
        composeTestRule.setContent {
            LibriaNowTheme {
                Box {
                    AnimeUpdatesContent(
                        screenState = HomeScreenState(isError = true),
                        navController = navController,
                        viewModel = vm
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag(ErrorSectionConstants.ERROR_SECTION_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun animeUpdatesContent_displays_animeLVGContainer_if_success() {
        composeTestRule.setContent {
            LibriaNowTheme {
                Box {
                    AnimeUpdatesContent(
                        screenState = HomeScreenState(),
                        navController = navController,
                        viewModel = vm
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag(AnimeLVGContainerConstants.ANIME_LVG_CONTAINER_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun nothingHereSection_displays_text() {
        composeTestRule.setContent {
            LibriaNowTheme {
                NothingHereSection()
            }
        }

        composeTestRule.onNodeWithText(NothingHereSectionConstants.NOTHING_HERE_TEXT).assertIsDisplayed()
    }

    @Test
    fun searchingContent_displays_nothingHereSection_if_query_is_empty() {
        composeTestRule.setContent {
            LibriaNowTheme {
                Box {
                    SearchingContent(
                        query = "",
                        titlesByQuery = flowOf(PagingData.empty<Data>()).collectAsLazyPagingItems(),
                        onCardClick = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithText(NothingHereSectionConstants.NOTHING_HERE_TEXT).assertIsDisplayed()
    }

    @Test
    fun searchingContent_displays_animeLVGContainer_if_query_is_not_empty_and_calls_onCardClick() {
        var clicked = 0

        val testItem = Data(id = 1)

        composeTestRule.setContent {
            LibriaNowTheme {
                Box {
                    SearchingContent(
                        query = "query",
                        titlesByQuery = flowOf(PagingData.from(listOf(testItem))).collectAsLazyPagingItems(),
                        onCardClick = { clicked = it }
                    )
                }
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(AnimeLVGContainerConstants.ANIME_LVG_CONTAINER_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(AnimeCardConstants.ANIME_CARD_TEST_TAG).performClick()

        assertEquals(1, clicked)
    }


    @Test
    fun searchingContent_displays_errorSection_if_error() {
        val lazyPagingItems = mockk<LazyPagingItems<Data>>(relaxed = true)

        val combinedLoadStates = CombinedLoadStates(
            refresh = LoadState.Error(Throwable("Test error")),
            prepend = LoadState.NotLoading(endOfPaginationReached = false),
            append = LoadState.NotLoading(endOfPaginationReached = false),
            source = LoadStates(
                refresh = LoadState.Error(Throwable("Test error")),
                prepend = LoadState.NotLoading(endOfPaginationReached = false),
                append = LoadState.NotLoading(endOfPaginationReached = false),
            ),
            mediator = null
        )

        every { lazyPagingItems.loadState } returns combinedLoadStates

        composeTestRule.setContent {
            LibriaNowTheme {
                Box {
                    SearchingContent(
                        query = "naruto",
                        titlesByQuery = lazyPagingItems,
                        onCardClick = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithText(ErrorSectionConstants.ERROR_SECTION_TEST_TAG).assertIsDisplayed()
    }
}