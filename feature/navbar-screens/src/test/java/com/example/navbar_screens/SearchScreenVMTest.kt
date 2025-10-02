package com.example.navbar_screens

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.test
import com.example.data.domain.SearchScreenRepo
import com.example.navbar_screens.home_screen.screen.HomeScreenIntent
import com.example.navbar_screens.search_screen.screen.SearchScreenIntent
import com.example.navbar_screens.search_screen.screen.SearchScreenState
import com.example.navbar_screens.search_screen.screen.SearchScreenVM
import com.example.navbar_screens.search_screen.screen.Season
import com.example.navbar_screens.search_screen.screen.SortedBy
import com.example.network.common.models.anime_list_with_pagination_response.Data
import com.example.network.common.models.common.Genre
import com.example.network.common.utils.NetworkErrors
import com.example.network.common.utils.NetworkResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchScreenVMTest {

    private lateinit var vm: SearchScreenVM

    private val repo: SearchScreenRepo = mockk(relaxed = true)
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        vm = SearchScreenVM(repo, dispatcher) // dispatcher у тебя и как main, и как io
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /// === States ===
    @Test
    fun `state is default on start`() {
        assertEquals(SearchScreenState(), vm.searchScreenState.value)
    }

    @Test
    fun `state changes correctly`() = runTest {
        vm.searchScreenState.test {
            vm.sendIntent(SearchScreenIntent.ChangeFiltersBSVisible)
            vm.sendIntent(SearchScreenIntent.ChangeReleaseEnd)
            vm.sendIntent(SearchScreenIntent.ChangeAnimeByFiltersLoading(true))
            vm.sendIntent(SearchScreenIntent.ChangeSortedBy(SortedBy.Popularity))
            vm.sendIntent(SearchScreenIntent.ChangeChosenSeasons(listOf(Season.Winter)))
            vm.sendIntent(SearchScreenIntent.ChangeChosenAnimeGenres(listOf(1)))
            vm.sendIntent(SearchScreenIntent.ChangeFromYear(12))
            vm.sendIntent(SearchScreenIntent.ChangeToYear(13))

            advanceUntilIdle()

            val after = expectMostRecentItem()

            assertTrue(after.isFilterBSVisible)
            assertFalse(after.releaseEnd)
            assertTrue(after.isAnimeByFiltersLoading)
            assertEquals(SortedBy.Popularity, after.sortedBy)
            assertEquals(listOf(Season.Winter), after.chosenSeasons)
            assertEquals(listOf(1), after.chosenAnimeGenres)
            assertEquals(12, after.fromYear)
            assertEquals(13, after.toYear)

            cancelAndIgnoreRemainingEvents()
        }
    }

    // === Data ===
    @Test
    fun `fetchAnimeGenres success updates state correctly`() = runTest {
        val genres = listOf(Genre(id = 1))
        coEvery { repo.getAnimeGenres() } returns NetworkResponse(
            error = NetworkErrors.SUCCESS,
            response = genres,
            label = "Success"
        )

        vm = SearchScreenVM(repo, dispatcher)
        advanceUntilIdle()

        val state = vm.searchScreenState.value
        assertEquals(genres, state.animeGenres)
        assertFalse(state.isAnimeGenresLoading)
        assertFalse(state.isAnimeGenresError)
    }

    @Test
    fun `fetchAnimeGenres error updates state with error`() = runTest {
        // given
        coEvery { repo.getAnimeGenres() } returns NetworkResponse(
            error = NetworkErrors.UNKNOWN,
            response = null
        )

        // when
        vm = SearchScreenVM(repo, dispatcher)
        advanceUntilIdle()

        // then
        val state = vm.searchScreenState.value
        assertTrue(state.isAnimeGenresError)
        assertFalse(state.isAnimeGenresLoading)
    }

    @Test
    fun `titlesByFilters returns correct paging data`() = runTest {
        // Data mock
        val collections = listOf(
            Data(id = 0),
            Data(id = 1)
        )

        // Flow mock
        val pagingData = PagingData.from(collections)
        val flow = flowOf(pagingData)

        coEvery { repo.getAnimeByFilters(any()) } returns flow

        // Check titles
        vm.animeByFilters.test {
            val item = awaitItem()

            val differ = AsyncPagingDataDiffer(
                diffCallback = diffCallbackStub(),
                updateCallback = noopListCallback(),
                mainDispatcher = Dispatchers.Main,
                workerDispatcher = Dispatchers.Main,
            )
            differ.submitData(item)

            advanceUntilIdle()

            assertEquals(2, differ.itemCount)
            assertEquals(0, differ.getItem(0)?.id)
            assertEquals(1, differ.getItem(1)?.id)

            cancelAndIgnoreRemainingEvents()
        }
    }

    // Stub
    private fun diffCallbackStub() = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Data, newItem: Data) =
            oldItem == newItem
    }

    // Stub
    private fun noopListCallback() = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}