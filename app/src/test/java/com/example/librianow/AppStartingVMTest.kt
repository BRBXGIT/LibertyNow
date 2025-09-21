package com.example.librianow

import app.cash.turbine.test
import com.example.data.domain.OnBoardingRepo
import com.example.data.domain.ThemeRepo
import com.example.librianow.app.AppStartingState
import com.example.librianow.app.AppStartingVM
import com.example.local.datastore.app_theme.ThemeState
import com.example.local.datastore.onboarding.OnBoardingState
import io.mockk.coEvery
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
class AppStartingVMTest {

    private lateinit var vm: AppStartingVM

    private val dispatcher = StandardTestDispatcher()
    private val themeRepo: ThemeRepo = mockk()
    private val onboardingRepo: OnBoardingRepo = mockk()


    private val theme = "some_theme"
    private val colorSystem = "color_system"
    private val useExpressive = true
    @Before
    fun setUp() {
        coEvery { onboardingRepo.onBoardingState } returns flowOf(OnBoardingState.Completed)
        coEvery { themeRepo.themeState } returns flowOf(ThemeState.Loaded)
        coEvery { themeRepo.colorSystemState } returns flowOf(ThemeState.Loaded)
        coEvery { themeRepo.theme } returns flowOf(theme)
        coEvery { themeRepo.colorSystem } returns flowOf(colorSystem)
        coEvery { themeRepo.useExpressive } returns flowOf(useExpressive)

        Dispatchers.setMain(dispatcher)
        vm = AppStartingVM(onboardingRepo, themeRepo, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `appStartingState is initial on start`() {
        assertEquals(AppStartingState(), vm.appStartingState.value)
    }

    @Test
    fun `observeStates calls repos methods and updates states`() = runTest {
        // Spin coroutine in vm init
        advanceUntilIdle()

        vm.appStartingState.test {
            val after = awaitItem()
            assertEquals(OnBoardingState.Completed, after.onBoardingState)
            assertEquals(ThemeState.Loaded, after.themeState)
            assertEquals(ThemeState.Loaded, after.colorSystemState)
            assertEquals(theme, after.theme)
            assertEquals(colorSystem, after.colorSystem)
            assertEquals(useExpressive, after.useExpressive)

            cancelAndIgnoreRemainingEvents()
        }
    }
}