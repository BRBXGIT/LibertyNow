package com.example.data

import com.example.data.data.LikesRepoImpl
import com.example.data.domain.LikesRepo
import com.example.network.auth.api.LikesApiInstance
import com.example.network.common.models.anime_list_with_pagination_response.AnimeListWithPaginationResponse
import com.example.network.common.models.anime_list_with_pagination_response.Data
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

class LikesRepoImplTest {

    private val api = mockk<LikesApiInstance>()
    private lateinit var repo: LikesRepo

    @Before
    fun setup() {
        repo = LikesRepoImpl(api)
    }

    @Test
    fun `get likes amount returns success if code is 200`() = runTest {
        val body = listOf(1, 2)
        val response = Response.success(body)

        val sessionToken = ""
        coEvery { api.getLikesAmount(sessionToken) } returns response

        val result = repo.getLikesAmount(sessionToken)

        assertEquals(NetworkErrors.SUCCESS, result.error)
        assertEquals("Всё отлично", result.label)
    }

    @Test
    fun `get likes amount returns NetworkError if code is not 200`() = runTest {
        val body = ResponseBody.create(MediaType.get("application/json"), "")
        val response = Response.error<List<Int>>(401, body)

        coEvery { api.getLikesAmount("") } returns response

        val result = repo.getLikesAmount("")

        assertEquals(NetworkErrors.UNAUTHORIZED, result.error)
        assertEquals("Кажется вы не авторизованы", result.label)
    }

    @Test
    fun `get likes amount returns Internet error if there is no connection`() = runTest {
        val sessionToken = ""
        coEvery { api.getLikesAmount(sessionToken) } throws UnknownHostException()

        val result = repo.getLikesAmount(sessionToken)

        assertEquals(NetworkErrors.INTERNET, result.error)
        assertEquals("Пожалуйста подключитесь к сети :)", result.label)
    }

    @Test
    fun `get likes returns success if code is 200`() = runTest {
        val body = AnimeListWithPaginationResponse(
            data = listOf(
                Data(
                    id = 0
                ),
                Data(
                    id = 1
                )
            )
        )
        val response = Response.success(body)

        val sessionToken = ""
        val limit = 2
        coEvery { api.getLikes(sessionToken, limit) } returns response

        val result = repo.getLikes(sessionToken, limit)

        assertEquals(listOf(0, 1), (result.response as AnimeListWithPaginationResponse).data.map { it.id })
        assertEquals(NetworkErrors.SUCCESS, result.error)
        assertEquals("Всё отлично", result.response)
    }

    @Test
    fun `get likes returns NetworkError if code is not 200`() = runTest {
        val body = ResponseBody.create(MediaType.get("application/json"), "")
        val response = Response.error<AnimeListWithPaginationResponse>(401, body)

        val sessionToken = ""
        val limit = 0
        coEvery { api.getLikes(sessionToken, limit) } returns response

        val result = repo.getLikes(sessionToken, limit)

        assertEquals(NetworkErrors.UNAUTHORIZED, result.error)
        assertEquals("Кажется вы не авторизованы", result.label)
    }

    @Test
    fun `get likes returns Internet error if there is no connection`() = runTest {
        val sessionToken = ""
        val limit = 0
        coEvery { api.getLikes(sessionToken, limit) } throws UnknownHostException()

        val result = repo.getLikes(sessionToken, limit)

        assertEquals(NetworkErrors.INTERNET, result.error)
        assertEquals("Пожалуйста подключитесь к сети :)", result.label)
    }

    @Test
    fun `add like returns success if code is 200`() = runTest {
        val body = listOf(0, 1)
        val response = Response.success(body)

        val sessionToken = ""
        coEvery { api.addLike(sessionToken, any()) } returns response

        val result = repo.addLike(sessionToken, 0)

        assertEquals(listOf(0, 1), (result.response as List<*>))
        assertEquals(NetworkErrors.SUCCESS, result.error)
        assertEquals("Всё отлично", result.response)
    }

    @Test
    fun `add like returns NetworkError if code is not 200`() = runTest {
        val body = ResponseBody.create(MediaType.get("application/json"), "")
        val response = Response.error<List<Int>>(401, body)

        val sessionToken = ""
        coEvery { api.addLike(sessionToken, any()) } returns response

        val result = repo.addLike(sessionToken, 0)

        assertEquals(NetworkErrors.UNAUTHORIZED, result.error)
        assertEquals("Кажется вы не авторизованы", result.label)
    }

    @Test
    fun `add like returns Internet error if there is no connection`() = runTest {
        val sessionToken = ""
        coEvery { api.addLike(sessionToken, any()) } throws UnknownHostException()

        val result = repo.addLike(sessionToken, 0)

        assertEquals(NetworkErrors.INTERNET, result.error)
        assertEquals("Пожалуйста подключитесь к сети :)", result.label)
    }

    @Test
    fun `remove like returns success if code is 200`() = runTest {
        val body = listOf(0, 1)
        val response = Response.success(body)

        val sessionToken = ""
        coEvery { api.removeLike(sessionToken, any()) } returns response

        val result = repo.removeLike(sessionToken, 0)

        assertEquals(listOf(0, 1), (result.response as List<*>))
        assertEquals(NetworkErrors.SUCCESS, result.error)
        assertEquals("Всё отлично", result.response)
    }

    @Test
    fun `remove like returns NetworkError if code is not 200`() = runTest {
        val body = ResponseBody.create(MediaType.get("application/json"), "")
        val response = Response.error<List<Int>>(401, body)

        val sessionToken = ""
        coEvery { api.removeLike(sessionToken, any()) } returns response

        val result = repo.removeLike(sessionToken, 0)

        assertEquals(NetworkErrors.UNAUTHORIZED, result.error)
        assertEquals("Кажется вы не авторизованы", result.label)
    }

    @Test
    fun `remove like returns Internet error if there is no connection`() = runTest {
        val sessionToken = ""
        coEvery { api.removeLike(sessionToken, any()) } throws UnknownHostException()

        val result = repo.removeLike(sessionToken, 0)

        assertEquals(NetworkErrors.INTERNET, result.error)
        assertEquals("Пожалуйста подключитесь к сети :)", result.label)
    }
}