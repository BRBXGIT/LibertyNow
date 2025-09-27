package com.example.navbar_screens

import app.cash.turbine.test
import com.example.navbar_screens.likes_screen.screen.LikesScreenIntent
import com.example.navbar_screens.likes_screen.screen.LikesScreenState
import com.example.navbar_screens.likes_screen.screen.LikesScreenVM
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LikesScreenVMTest {

    private lateinit var vm: LikesScreenVM

    @Before
    fun setUp() {
        vm = LikesScreenVM()
    }

    @Test
    fun `state is default on start`() = runTest {
        assertEquals(LikesScreenState(), vm.likesScreenState.value)
    }

    @Test
    fun `state updates correctly`() = runTest {
        vm.likesScreenState.test {
            val initial = awaitItem()

            assertEquals(LikesScreenState(), initial)

            vm.sendIntent(LikesScreenIntent.ChangeIsSearching)
            assertTrue(awaitItem().isSearching)

            val query = "query"
            vm.sendIntent(LikesScreenIntent.ChangeQuery(query))
            assertEquals(query, awaitItem().query)

            cancelAndIgnoreRemainingEvents()
        }
    }
}