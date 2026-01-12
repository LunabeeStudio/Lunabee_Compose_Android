package com.lunabee.lbextensions

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals

class FlowExtTest {
    @Test
    fun `Collect flow withPreviousState test`(): TestResult = runTest {
        val flow = flowOf(null, 1, 2, null, 3)
        val expected = listOf(
            null to null,
            null to 1,
            1 to 2,
            2 to null,
            null to 3,
        )
        val actual = flow.withPreviousState().toList()
        assertContentEquals(expected, actual)
    }
}
