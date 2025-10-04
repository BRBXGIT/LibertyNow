package com.example.navbar_screens

import app.cash.turbine.test
import com.example.navbar_screens.more_screen.screen.MoreScreenIntent
import com.example.navbar_screens.more_screen.screen.MoreScreenState
import com.example.navbar_screens.more_screen.screen.MoreScreenVM
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MoreScreenVMTest {

    private lateinit var vm: MoreScreenVM

    @Before
    fun setUp() {
        vm = MoreScreenVM()
    }

    @Test
    fun `state is default on start`() {
        assertEquals(MoreScreenState(), vm.moreScreenState.value)
    }

    @Test
    fun `state updates correctly`() = runTest {
        vm.moreScreenState.test {
            awaitItem()

            vm.sendIntent(MoreScreenIntent.ChangeIsQuitAdVisible)

            val after = awaitItem()

            assertTrue(after.isQuitADVisible)

            cancelAndIgnoreRemainingEvents()
        }
    }
}