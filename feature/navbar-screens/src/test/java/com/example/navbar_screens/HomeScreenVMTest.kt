package com.example.navbar_screens

import com.example.data.data.HomeScreenRepoImpl
import com.example.data.domain.HomeScreenRepo
import com.example.navbar_screens.home_screen.screen.HomeScreenIntent
import com.example.navbar_screens.home_screen.screen.HomeScreenVM
import com.example.network.common.models.anime_list_response.AnimeListResponse
import com.example.network.home_screen.api.HomeScreenApiInstance
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeScreenVMTest {

    private val testDispatcher = StandardTestDispatcher()

    private val api = mockk<HomeScreenApiInstance>()
    private lateinit var repo: HomeScreenRepo
    private lateinit var viewModel: HomeScreenVM

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repo = HomeScreenRepoImpl(api)
        viewModel = HomeScreenVM(
            repository = repo,
            dispatcherIo = testDispatcher,
            dispatcherMain = testDispatcher
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchTitlesUpdates sets state with titles on success`() = runTest {
        val response = NetworkResponse(
            response = AnimeListResponse(),
            error = NetworkErrors.SUCCESS,
            label = ""
        )
        coEvery { repo.getTitlesUpdates() } returns response

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.homeScreenState.value
        assertFalse(state.isLoading)
        assertEquals(response.response, state.titlesUpdates)
        assertFalse(state.isError)
    }

    @Test
    fun `fetch random title sets state with title on success`() = runTest {
        var completed = false
        coEvery { repo.getRandomTitle() } returns

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.homeScreenState.value
        assertFalse(state.isLoading)
        assertTrue(completed)
        assertFalse(state.isError)
    }
}