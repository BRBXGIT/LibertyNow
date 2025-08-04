package com.example.data

import com.example.common.functions.NetworkErrors
import com.example.data.data.AnimeScreenRepoImpl
import com.example.data.domain.AnimeScreenRepo
import com.example.network.anime_screen.api.AnimeScreenApiInstance
import com.example.network.anime_screen.models.anime_details_response.AnimeDetailsResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AnimeScreenRepoImplTest {

    private val api = mockk<AnimeScreenApiInstance>()
    private lateinit var repo: AnimeScreenRepo

    @Before
    fun setup() {
        repo = AnimeScreenRepoImpl(api)
    }

    @Test
    fun `returns success when response is 200`() = runTest {
        val body = AnimeDetailsResponse()
        val response = Response.success(body)

        coEvery { api.getAnime(1) } returns response

        val result = repo.getAnime(1)

        assertEquals(NetworkErrors.SUCCESS, result.error)
        assertEquals(body, result.response)
    }

    @Test
    fun `returns NetworkException when response is not 200`() = runTest {
        val body = ResponseBody.create(MediaType.get("application/json"), "")
        val response = Response.error<AnimeDetailsResponse>(401, body)

        coEvery { api.getAnime(1) } returns response

        val result = repo.getAnime(1)

        assertEquals(NetworkErrors.UNAUTHORIZED, result.error)
        assertEquals("Кажется вы не авторизованы", result.label)
    }
}