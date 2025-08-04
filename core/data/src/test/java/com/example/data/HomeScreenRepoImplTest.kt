package com.example.data

import com.example.common.functions.NetworkErrors
import com.example.data.data.HomeScreenRepoImpl
import com.example.data.domain.HomeScreenRepo
import com.example.network.common.models.anime_list_response.AnimeListResponse
import com.example.network.home_screen.api.HomeScreenApiInstance
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException

class HomeScreenRepoImplTest {

    private val api = mockk<HomeScreenApiInstance>()
    private lateinit var repo: HomeScreenRepo

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        repo = HomeScreenRepoImpl(api)
    }

    // Title updates tests
    @Test
    fun `get title updates returns success when response is 200`() = runTest {
        val body = AnimeListResponse()
        val response = Response.success(body)

        coEvery { api.getTitlesUpdates() } returns response

        val result = repo.getTitlesUpdates()

        assertEquals(NetworkErrors.SUCCESS, result.error)
        assertEquals(body, result.response)
    }

    @Test
    fun `get title updates returns NetworkException when response is not 200`() = runTest {
        val body = ResponseBody.create(MediaType.get("application/json"), "")
        val response = Response.error<AnimeListResponse>(401, body)

        coEvery { api.getTitlesUpdates() } returns response

        val result = repo.getTitlesUpdates()

        assertEquals(NetworkErrors.UNAUTHORIZED, result.error)
        assertEquals("Кажется вы не авторизованы", result.label)
    }

    @Test
    fun `get title updates returns internet exception if there is no connection`() = runTest {
        coEvery { api.getTitlesUpdates() } throws UnknownHostException()

        val result = repo.getTitlesUpdates()

        assertEquals(NetworkErrors.INTERNET, result.error)
        assertEquals("Пожалуйста подключитесь к сети :)", result.label)
    }

    // Random title tests
    @Test
    fun `get random title returns success when response is 200`() = runTest {
        val body = AnimeListResponse()
        val response = Response.success(body)

        coEvery { api.getRandomTitle() } returns response

        val result = repo.getRandomTitle()

        assertEquals(NetworkErrors.SUCCESS, result.error)
        assertEquals(body, result.response)
    }

    @Test
    fun `get random title returns NetworkException when response is not 200`() = runTest {
        val body = ResponseBody.create(MediaType.get("application/json"), "")
        val response = Response.error<AnimeListResponse>(401, body)

        coEvery { api.getRandomTitle() } returns response

        val result = repo.getRandomTitle()

        assertEquals(NetworkErrors.UNAUTHORIZED, result.error)
        assertEquals("Кажется вы не авторизованы", result.label)
    }

    @Test
    fun `get random title returns internet exception if there is no connection`() = runTest {
        coEvery { api.getRandomTitle() } throws UnknownHostException()

        val result = repo.getRandomTitle()

        assertEquals(NetworkErrors.INTERNET, result.error)
        assertEquals("Пожалуйста подключитесь к сети :)", result.label)
    }
}