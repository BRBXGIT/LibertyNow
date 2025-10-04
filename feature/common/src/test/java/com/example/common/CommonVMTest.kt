package com.example.common

import app.cash.turbine.test
import com.example.common.common.CommonIntent
import com.example.common.common.CommonState
import com.example.common.common.CommonVM
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CommonVMTest {

    private lateinit var vm: CommonVM

    @Before
    fun setUp() {
        vm = CommonVM()
    }

    @Test
    fun `state is default on start`() {
        assertEquals(CommonState(), vm.commonState.value)
    }

    @Test
    fun `state updates correctly`() = runTest {
        vm.commonState.test {
            awaitItem()

            vm.sendIntent(CommonIntent.ChangeNavIndex(2))

            val after = awaitItem()

            assertEquals(2, after.selectedNavBarIndex)
        }
    }
}