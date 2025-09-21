package com.example.onboarding_screen

import com.example.data.domain.OnBoardingRepo
import com.example.onboarding_screen.screen.OnBoardingScreenIntent
import com.example.onboarding_screen.screen.OnBoardingScreenVM
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnBoardingScreenVMTest {

    private lateinit var vm: OnBoardingScreenVM

    private val dispatcher = StandardTestDispatcher()
    private val repo: OnBoardingRepo = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        vm = OnBoardingScreenVM(repo, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `saveIsOnBoardingCompleted calls repo method`() = runTest {
        coEvery { repo.saveIsOnBoardingCompleted(any()) } just Runs

        vm.sendIntent(OnBoardingScreenIntent.SaveIsOnBoardingCompleted)

        advanceUntilIdle()

        coVerify { repo.saveIsOnBoardingCompleted(any()) }
    }
}