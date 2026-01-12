package com.lunabee.lbtest

import kotlin.test.assertFailsWith

/**
 * Not [assertBlock]
 */
fun assertNot(assertBlock: () -> Unit) {
    assertFailsWith(AssertionError::class) {
        assertBlock()
    }
}
