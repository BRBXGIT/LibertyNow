package com.example.data

import com.example.data.data.AuthRepoImpl
import com.example.data.domain.AuthRepo
import com.example.local.datastore.auth.AuthManager
import com.example.network.auth.api.AuthApiInstance
import com.example.network.auth.models.session_token_response.SessionTokenResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException

class AuthRepoImplTest {

    private val api = mockk<AuthApiInstance>()
    private val authManager = mockk<AuthManager>()

    private lateinit var repo: AuthRepo

    @Before
    fun setup() {
        every { authManager.userSessionTokenFlow } returns flowOf(null)
        repo = AuthRepoImpl(authManager, api)
    }

    @Test
    fun `get session token returns success if code 200`() = runTest {
        val response = Response.success(SessionTokenResponse())
        coEvery { api.getSessionToken(any()) } returns response

        val email = ""
        val password = ""
        val result = repo.getSessionToken(email, password)

        assertEquals(NetworkErrors.SUCCESS, result.error)
    }

    @Test
    fun `get session token returns incorrect email or password if it is incorrect`() = runTest {
        val response = Response.success(SessionTokenResponse(error = "Не удалось авторизоваться. Неправильные логин/пароль"))
        coEvery { api.getSessionToken(any()) } returns response

        val email = ""
        val password = ""
        val result = repo.getSessionToken(email, password)

        assertEquals(NetworkErrors.INCORRECT_EMAIL_OR_PASSWORD, result.error)
    }

    @Test
    fun `get session token returns error if the code is not 200`() = runTest {
        val body = ResponseBody.create(MediaType.get("application/json"), "")
        val response = Response.error<SessionTokenResponse>(401, body)
        coEvery { api.getSessionToken(any()) } returns response

        val email = ""
        val password = ""
        val result = repo.getSessionToken(email, password)

        assertEquals(NetworkErrors.UNAUTHORIZED, result.error)
    }

    @Test
    fun `get session token returns InternetException if there is no connection`() = runTest {
        coEvery { api.getSessionToken(any()) } throws UnknownHostException()

        val email = ""
        val password = ""
        val result = repo.getSessionToken(email, password)

        assertEquals(NetworkErrors.INTERNET, result.error)
    }
}