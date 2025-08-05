package com.example.data

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.LoadState
import com.example.common.functions.NetworkErrors
import com.example.common.functions.NetworkException
import com.example.data.data.SearchScreenRepoImpl
import com.example.data.domain.SearchScreenRepo
import com.example.network.common.models.anime_list_with_pagination_response.AnimeListWithPaginationResponse
import com.example.network.common.models.anime_list_with_pagination_response.Data
import com.example.network.common.models.anime_list_with_pagination_response.Meta
import com.example.network.common.models.anime_list_with_pagination_response.Pagination
import com.example.network.common.models.common.Genre
import com.example.network.search_screen.api.SearchScreenApiInstance
import com.example.network.search_screen.models.anime_by_filters_request.AnimeByFiltersRequest
import com.example.network.search_screen.models.anime_by_filters_request.F
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException

class SearchScreenRepoImplPagingTest {

    private val api = mockk<SearchScreenApiInstance>()
    private lateinit var repo: SearchScreenRepo

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repo = SearchScreenRepoImpl(api)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get titles by filters returns correct paging data`() = runTest {
        val query = AnimeByFiltersRequest(
            f = F(
                genres = listOf(0, 1)
            ),
            limit = 2,
            page = 1
        )
        val mockDataList = listOf(
            Data(id = 0, genres = listOf(Genre(id = 0))),
            Data(id = 1, genres = listOf(Genre(id = 1)))
        )
        val mockResponse = AnimeListWithPaginationResponse(
            data = mockDataList,
            meta = Meta(Pagination(currentPage = 1, count = 2))
        )

        coEvery {
            api.getAnimeByFilters(any())
        } returns Response.success(mockResponse)

        val flow = repo.getAnimeByFilters(query)

        val differ = AsyncPagingDataDiffer(
            diffCallback = DiffCallback(),
            updateCallback = NoopListCallback(),
            workerDispatcher = testDispatcher,
            mainDispatcher = testDispatcher
        )

        val job = launch {
            flow.collect {
                differ.submitData(it)
            }
        }

        advanceUntilIdle()

        // Then
        assertEquals(2, differ.itemCount)
        assertEquals(0, differ.snapshot()[0]?.id)
        assertEquals(0, differ.snapshot()[0]?.genres[0]?.id)
        assertEquals(1, differ.snapshot()[1]?.id)
        assertEquals(1, differ.snapshot()[1]?.genres[0]?.id)

        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get titles by filters returns NetworkError if code is not 200`() = runTest {
        val body = ResponseBody.create(MediaType.get("application/json"), "")
        val response = Response.error<AnimeListWithPaginationResponse>(401, body)

        coEvery { api.getAnimeByFilters(any()) } returns response

        val query = AnimeByFiltersRequest(
            f = F(
                genres = listOf(0, 1)
            ),
            limit = 2,
            page = 1
        )
        val flow = repo.getAnimeByFilters(query)

        val differ = AsyncPagingDataDiffer(
            diffCallback = DiffCallback(),
            updateCallback = NoopListCallback(),
            workerDispatcher = testDispatcher,
            mainDispatcher = testDispatcher
        )

        val job = launch {
            flow.collect {
                differ.submitData(it)
            }
        }

        advanceUntilIdle()

        assertEquals(NetworkErrors.UNAUTHORIZED, ((differ.loadStateFlow.first().refresh as LoadState.Error).error as NetworkException).error)
        assertEquals("Кажется вы не авторизованы", ((differ.loadStateFlow.first().refresh as LoadState.Error).error as NetworkException).label)

        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get titles by query returns internet exception if there is no connection`() = runTest {
        coEvery { api.getAnimeByFilters(any()) } throws UnknownHostException()

        val query = AnimeByFiltersRequest(
            f = F(
                genres = listOf(0, 1)
            ),
            limit = 2,
            page = 1
        )
        val flow = repo.getAnimeByFilters(query)

        val differ = AsyncPagingDataDiffer(
            diffCallback = DiffCallback(),
            updateCallback = NoopListCallback(),
            workerDispatcher = testDispatcher,
            mainDispatcher = testDispatcher
        )

        val job = launch {
            flow.collect {
                differ.submitData(it)
            }
        }

        advanceUntilIdle()
        // Then
        assertEquals(0, differ.itemCount)
        assertEquals(NetworkErrors.INTERNET, ((differ.loadStateFlow.first().refresh as LoadState.Error).error as NetworkException).error)
        assertEquals("Пожалуйста подключитесь к сети :)", ((differ.loadStateFlow.first().refresh as LoadState.Error).error as NetworkException).label)

        job.cancel()
    }
}