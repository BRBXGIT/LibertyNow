package com.example.common

import app.cash.turbine.test
import com.example.common.auth.AuthIntent
import com.example.common.auth.AuthVM
import com.example.data.domain.AuthRepo
import com.example.data.domain.LikesRepo
import com.example.design_system.snackbars.SnackbarController
import com.example.local.datastore.auth.LoggingState
import com.example.network.auth.models.session_token_response.SessionTokenResponse
import com.example.network.common.models.anime_list_with_pagination_response.AnimeListWithPaginationResponse
import com.example.network.common.models.anime_list_with_pagination_response.Data
import com.example.network.common.utils.NetworkErrors
import com.example.network.common.utils.NetworkResponse
import io.mockk.Runs
import io.mockk.awaits
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
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

    private fun <T> successResponse(response: T?): NetworkResponse<T> {
        return NetworkResponse(
            response = response,
            error = NetworkErrors.SUCCESS,
            label = "Success"
        )
    }

    private fun <T> errorResponse(response: T?): NetworkResponse<T> {
        return NetworkResponse(
            response = response,
            error = NetworkErrors.UNKNOWN,
            label = "Unknown error"
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        // Auth repo mocks
        coEvery { authRepo.loggingState } returns flowOf(LoggingState.LoggedIn)
        coEvery { authRepo.userSessionToken } returns flowOf("token")

        // Likes repo mocks
        coEvery { likesRepo.getLikesAmount(any()) } returns successResponse(listOf(1, 2, 3))
        coEvery { likesRepo.getLikes(any(), any()) } returns successResponse(
            AnimeListWithPaginationResponse(data = listOf(Data(id = 1), Data(id = 2)))
        )

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
        coEvery { authRepo.getSessionToken(any(), any()) } returns successResponse(
            SessionTokenResponse(token = "token")
        )
        coEvery { authRepo.saveUserSessionToken(any()) } just Runs

        vm.sendIntent(AuthIntent.GetSessionToken)

        advanceUntilIdle()

        coVerify { authRepo.saveUserSessionToken("token") }
    }

    @Test
    fun `getSessionToken make snackbar on error`() = runTest {
        SnackbarController.events.test {
            coEvery { authRepo.getSessionToken(any(), any()) } returns errorResponse(
                SessionTokenResponse()
            )

            vm.sendIntent(AuthIntent.GetSessionToken)

            advanceUntilIdle()

            val after = awaitItem()

            assertNotNull(after.action)
        }
    }

    @Test
    fun `clearSessionToken calls repo method`() = runTest {
        coEvery { authRepo.clearUserSessionToken() } just Runs

        vm.sendIntent(AuthIntent.ClearSessionToken)

        advanceUntilIdle()

        coVerify { authRepo.clearUserSessionToken() }
    }

    // === Likes ===
    @Test
    fun `addLikes adds title to likes on success`() = runTest {
        coEvery { likesRepo.addLike(any(), any()) } returns successResponse(Unit)

        vm.authState.test {
            vm.sendIntent(AuthIntent.AddLike(title = Data(id = 3)))

            advanceUntilIdle()

            val after = expectMostRecentItem()
            assertEquals(listOf(Data(id = 3)), after.likes)
        }
    }
}