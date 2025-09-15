package com.example.data

import com.example.data.data.SearchScreenRepoImpl
import com.example.data.domain.SearchScreenRepo
import com.example.network.common.models.common.Genre
import com.example.network.search_screen.api.SearchScreenApiInstance
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException

class SearchScreenRepoImplTest {

    private val api = mockk<SearchScreenApiInstance>()
    private lateinit var repo: SearchScreenRepo

    @Before
    fun setup() {
        repo = SearchScreenRepoImpl(api)
    }

    @Test
    fun `get anime genres returns success if the code is 200`() = runTest {
        val body = listOf(Genre(id = 0), Genre(id = 1))
        val response = Response.success(body)

        coEvery { api.getAnimeGenres() } returns response

        val result = repo.getAnimeGenres()

        assertEquals(NetworkErrors.SUCCESS, result.error)
        assertEquals(body, result.response)
    }

    @Test
    fun `get anime genres returns NetworkError if the code is not 200`() = runTest {
        val body = ResponseBody.create(MediaType.get("application/json"), "")
        val response = Response.error<List<Genre>>(401, body)

        coEvery { api.getAnimeGenres() } returns response

        val result = repo.getAnimeGenres()

        assertEquals(NetworkErrors.UNAUTHORIZED, result.error)
        assertEquals("Кажется вы не авторизованы", result.label)
    }

    @Test
    fun `get anime genres returns InternetException if there is no connection`() = runTest {
        coEvery { api.getAnimeGenres() } throws UnknownHostException()

        val result = repo.getAnimeGenres()

        assertEquals(NetworkErrors.INTERNET, result.error)
        assertEquals("Пожалуйста подключитесь к сети :)", result.label)
    }
}