package com.example.common

import app.cash.turbine.test
import com.example.common.auth.AuthIntent
import com.example.common.auth.AuthVM
import com.example.data.domain.AuthRepo
import com.example.data.domain.LikesRepo
import com.example.local.datastore.auth.LoggingState
import com.example.network.auth.models.session_token_response.SessionTokenResponse
import com.example.network.common.utils.NetworkErrors
import com.example.network.common.utils.NetworkResponse
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
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
class AuthVMTest {

    private lateinit var vm: AuthVM

    private val authRepo: AuthRepo = mockk()
    private val likesRepo: LikesRepo = mockk()
    private val dispatcher = StandardTestDispatcher()

    // Todo add mock for likes to avoid errors
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        coEvery { authRepo.loggingState } returns flowOf(LoggingState.LoggedIn)
        coEvery { authRepo.userSessionToken } returns flowOf("token")

        vm = AuthVM(authRepo, likesRepo, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // === Auth & data ===
    @Test
    fun `observeSessionTokens updates state`() = runTest {
        vm.authState.test {
            advanceUntilIdle()

            val item = expectMostRecentItem()

            assertEquals(LoggingState.LoggedIn, item.isLogged)
            assertEquals("token", item.sessionToken)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getSessionToken calls repo methods on success`() = runTest {
        coEvery { authRepo.getSessionToken(any(), any()) } returns NetworkResponse(
            response = SessionTokenResponse(token = "token"),
            error = NetworkErrors.SUCCESS,
            label = "Success"
        )
        coEvery { authRepo.saveUserSessionToken(any()) } just Runs

        vm.sendIntent(AuthIntent.GetSessionToken)

        advanceUntilIdle()

        coVerify { authRepo.saveUserSessionToken("token") }
    }
}