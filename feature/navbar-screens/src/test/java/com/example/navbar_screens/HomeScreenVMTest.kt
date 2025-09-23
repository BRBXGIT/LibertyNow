package com.example.navbar_screens

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.test
import com.example.data.domain.HomeScreenRepo
import com.example.design_system.snackbars.SnackbarController
import com.example.navbar_screens.home_screen.screen.HomeScreenIntent
import com.example.navbar_screens.home_screen.screen.HomeScreenState
import com.example.navbar_screens.home_screen.screen.HomeScreenVM
import com.example.network.common.models.anime_list_response.AnimeListResponse
import com.example.network.common.models.anime_list_response.AnimeListResponseItem
import com.example.network.common.models.anime_list_with_pagination_response.Data
import com.example.network.common.utils.NetworkErrors
import com.example.network.common.utils.NetworkResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenVMTest {

    private lateinit var vm: HomeScreenVM

    private val dispatcher = StandardTestDispatcher()
    private val repo: HomeScreenRepo = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        val successResult = NetworkResponse(
            response = AnimeListResponse(),
            error = NetworkErrors.SUCCESS,
            label = "Success"
        )
        coEvery { repo.getTitlesUpdates() } returns successResult

        vm = HomeScreenVM(repo, dispatcher, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // === Data tests ===
    @Test
    fun `fetchTitlesUpdates change state correctly on success`() = runTest {
        val vm = HomeScreenVM(repo, dispatcher, dispatcher)
        val successResponse = NetworkResponse(
            response = AnimeListResponse().apply {
                add(AnimeListResponseItem(id = 0))
            },
            error = NetworkErrors.SUCCESS,
            label = "Success"
        )

        coEvery { repo.getTitlesUpdates() } returns successResponse

        vm.homeScreenState.test {
            awaitItem()

            advanceUntilIdle()

            val after = awaitItem()
            assertEquals(0, after.titlesUpdates[0].id)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchTitlesUpdates sends snackbar on error`() = runTest {
        val vm = HomeScreenVM(repo, dispatcher, dispatcher)
        val errorResponse = NetworkResponse(
            response = AnimeListResponse(),
            error = NetworkErrors.UNKNOWN,
            label = "UNKNOWN"
        )

        coEvery { repo.getTitlesUpdates() } returns errorResponse

        advanceUntilIdle()

        SnackbarController.events.test {
            val after = awaitItem()
            assertNotNull(after.action)
            assertEquals("Retry", after.action?.name)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `titlesByQuery emits data when query change`() = runTest {
        // Data mock
        val collections = listOf(
            Data(id = 0),
            Data(id = 1)
        )

        // Flow mock
        val pagingData = PagingData.from(collections)
        val flow = flowOf(pagingData)

        coEvery { repo.getTitlesByQuery(any()) } returns flow

        // Check titles
        vm.titlesByQuery.test {
            vm.sendIntent(HomeScreenIntent.ChangeQuery("query"))
            advanceUntilIdle()

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

    @Test
    fun `fetchRandomTitle calls onComplete on success`() = runTest {
        val successResult = NetworkResponse(
            response = AnimeListResponse().apply {
                add(AnimeListResponseItem(id = 0))
            },
            error = NetworkErrors.SUCCESS,
            label = "Success"
        )
        coEvery { repo.getRandomTitle() } returns successResult

        var id = 3
        vm.sendIntent(
            HomeScreenIntent.FetchRandomTitle(
                onComplete = { id = it }
            )
        )

        advanceUntilIdle()

        assertEquals(0, id)
    }

    @Test
    fun `fetchRandomTitles sends snackbar on error`() = runTest {
        val errorResult = NetworkResponse(
            response = AnimeListResponse(),
            error = NetworkErrors.UNKNOWN,
            label = "UNKNOWN"
        )
        coEvery { repo.getRandomTitle() } returns errorResult

        SnackbarController.events.test {
            vm.sendIntent(HomeScreenIntent.FetchRandomTitle {})

            advanceUntilIdle()

            val after = awaitItem()
            assertNotNull(after.action)
            assertEquals("Retry", after.action?.name)

            cancelAndIgnoreRemainingEvents()
        }
    }

    // === States tests ===
    @Test
    fun `state is default on start`() {
        assertEquals(HomeScreenState(), vm.homeScreenState.value)
    }

    @Test
    fun `intents update states correctly`() = runTest {
        val query = "query"

        vm.homeScreenState.test {
            awaitItem()

            vm.sendIntent(HomeScreenIntent.ChangeIsLoading(true))
            vm.sendIntent(HomeScreenIntent.ChangeIsSearching)
            vm.sendIntent(HomeScreenIntent.ChangeQuery(query))

            advanceUntilIdle()

            val after = awaitItem()
            assertTrue(after.isLoading)
            assertTrue(after.isSearching)
            assertEquals(query, after.query)

            cancelAndIgnoreRemainingEvents()
        }
    }
}