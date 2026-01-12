package com.lunabee.lbcore.helper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
class LBLoadingVisibilityDelayDelegateTest {

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun isActive_test(): TestResult = runTest {
        val actualShowLoading = mutableListOf<Boolean>()
        val delayMs = 2L

        val delegate = LBLoadingVisibilityDelayDelegate(
            minLoadingShowDuration = delayMs.milliseconds,
            delayBeforeShow = delayMs.milliseconds,
        )

        // Request show
        delegate.delayShowLoading { actualShowLoading += true }
        assertTrue(delegate.isActive())

        testDispatcher.scheduler.advanceTimeBy(delayMs)
        testDispatcher.scheduler.runCurrent()
        assertTrue(delegate.isActive()) // still true to handle minLoadingShowDuration

        testDispatcher.scheduler.advanceTimeBy(delayMs)
        testDispatcher.scheduler.runCurrent()
        assertFalse(delegate.isActive()) // false after minLoadingShowDuration

        // Request hide
        delegate.delayHideLoading { actualShowLoading += false }
        assertTrue(delegate.isActive()) // briefly true has hide job did not run yet

        testDispatcher.scheduler.runCurrent()

        assertFalse(delegate.isActive())
    }

    @Test
    fun delayShowLoading_during_pending_hide_test(): TestResult = runTest {
        val actualShowLoading = mutableListOf<Boolean>()
        val delayMs = 2L

        val delegate = LBLoadingVisibilityDelayDelegate(
            minLoadingShowDuration = delayMs.milliseconds,
            delayBeforeShow = delayMs.milliseconds,
        )

        // Request show
        delegate.delayShowLoading { actualShowLoading += true }
        // Advance delayBeforeShow + run delayShowLoading lambda
        testDispatcher.scheduler.advanceTimeBy(delayMs)
        testDispatcher.scheduler.runCurrent()
        // Request hide
        delegate.delayHideLoading { actualShowLoading += false }
        // Advance partially minLoadingShowDuration
        testDispatcher.scheduler.advanceTimeBy(delayMs / 2)
        // Re-request show
        delegate.delayShowLoading { actualShowLoading += true }
        // Advance to last state
        testDispatcher.scheduler.advanceUntilIdle()

        val expectedShowLoading = listOf(true)
        assertContentEquals(expectedShowLoading, actualShowLoading, "actual = ${actualShowLoading.joinToString()}")
    }

    @Test
    fun delayShowLoading_updateProgress_test(): TestResult = runTest {
        val actualShowLoading = mutableListOf<Boolean>()
        val delayMs = 2L

        val delegate = LBLoadingVisibilityDelayDelegate(
            minLoadingShowDuration = delayMs.milliseconds,
            delayBeforeShow = delayMs.milliseconds,
        )

        var progress = -1

        // Request show
        delegate.delayShowLoading(
            updateLoading = { progress = 0 },
            showLoading = { actualShowLoading += true },
        )
        // Advance delayBeforeShow + run delayShowLoading lambda (show + update)
        testDispatcher.scheduler.advanceTimeBy(delayMs)
        testDispatcher.scheduler.runCurrent()

        assertEquals(0, progress)

        // Call delayShowLoading to update progress
        delegate.delayShowLoading(
            updateLoading = { progress = 1 },
            showLoading = { assertTrue(false, "Should not be called") },
        )

        testDispatcher.scheduler.runCurrent()

        assertEquals(1, progress)
    }
}
