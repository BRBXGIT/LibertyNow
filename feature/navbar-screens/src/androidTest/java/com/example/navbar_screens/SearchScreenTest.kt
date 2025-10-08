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
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.anime_screen.navigation.AnimeScreenRoute
import com.example.common.common.CommonState
import com.example.common.common.CommonVM
import com.example.design_system.sections.error_section.ErrorSectionConstants
import com.example.navbar_screens.common.AnimeLVGContainerConstants
import com.example.navbar_screens.search_screen.screen.SearchScreen
import com.example.navbar_screens.search_screen.screen.SearchScreenState
import com.example.navbar_screens.search_screen.screen.SearchScreenVM
import com.example.navbar_screens.search_screen.sections.FiltersBSConstants
import com.example.navbar_screens.search_screen.sections.SearchScreenTopBarConstants
import com.example.network.common.models.anime_list_with_pagination_response.Data
import com.example.network.common.models.common.Name
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

    @get:Rule()
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val navController = mockk<NavController>(relaxed = true)
    private val commonVM = mockk<CommonVM>(relaxed = true)
    private val vm = mockk<SearchScreenVM>(relaxed = true)

    @Before
    fun setUp() {
        every { vm.searchScreenState } returns MutableStateFlow(SearchScreenState())
        every { vm.animeByFilters } returns emptyFlow()
        every { commonVM.commonState } returns MutableStateFlow(CommonState())
    }

    @Test
    fun searchScreen_isDisplayed() {
        composeTestRule
            .onNodeWithTag(AnimeLVGContainerConstants.ANIME_LVG_CONTAINER_TEST_TAG)
            .assertExists()
    }

    @Test
    fun showsErrorSection_whenAnimeByFiltersErrorIsTrue() {
        val errorState = MutableStateFlow(SearchScreenState(isAnimeByFiltersError = true))
        every { vm.searchScreenState } returns errorState

        composeTestRule.setContent {
            val fakePagingItems = flowOf(PagingData.empty<Data>()).collectAsLazyPagingItems()
            val state by vm.searchScreenState.collectAsStateWithLifecycle()

            SearchScreen(
                screenState = state,
                commonState = CommonState(),
                animeByFilters = fakePagingItems,
                onIntent = {},
                onCommonIntent = {},
                navController = navController,
                useExpressive = false,
            )
        }

        composeTestRule
            .onNodeWithTag(ErrorSectionConstants.ERROR_SECTION_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun bottomSheet_appears_whenFiltersButtonClicked() {
        val stateFlow = MutableStateFlow(SearchScreenState(isFilterBSVisible = false))
        every { vm.searchScreenState } returns stateFlow

        composeTestRule.setContent {
            val fakePagingItems = flowOf(PagingData.empty<Data>()).collectAsLazyPagingItems()
            val state by vm.searchScreenState.collectAsStateWithLifecycle()

            SearchScreen(
                screenState = state,
                commonState = CommonState(),
                animeByFilters = fakePagingItems,
                onIntent = {},
                onCommonIntent = {},
                navController = navController,
                useExpressive = false,
            )
        }

        composeTestRule.onNodeWithTag(SearchScreenTopBarConstants.ACTION_BUTTON_TEST_TAG).performClick()

        stateFlow.value = stateFlow.value.copy(isFilterBSVisible = true)

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag(FiltersBSConstants.BOTTOM_SHEET_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun clickingOnAnimeCard_triggersNavigation() {
        val fakeAnime = Data(
            id = 1,
            name = Name(main = "Naruto"),
        )
        val pagingItems = flowOf(PagingData.from(listOf(fakeAnime)))

        every { vm.animeByFilters } returns pagingItems

        composeTestRule.setContent {
            val fakePagingItems = vm.animeByFilters.collectAsLazyPagingItems()

            SearchScreen(
                screenState = SearchScreenState(isAnimeByFiltersError = true),
                commonState = CommonState(),
                animeByFilters = fakePagingItems,
                onIntent = {},
                onCommonIntent = {},
                navController = navController,
                useExpressive = false,
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Naruto").assertIsDisplayed()
        composeTestRule.onNodeWithText("Naruto").performClick()

        verify { navController.navigate(AnimeScreenRoute(1)) }
    }
}