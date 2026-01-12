package com.lunabee.lbcore

import com.lunabee.lbtest.assertNot
import kotlin.test.Test
import kotlin.test.assertContains

class AssertsTest {
    @Test
    fun assertNot_test() {
        val data = listOf(0, 1, 2)
        assertNot { assertContains(data, 3) }
    }
}
