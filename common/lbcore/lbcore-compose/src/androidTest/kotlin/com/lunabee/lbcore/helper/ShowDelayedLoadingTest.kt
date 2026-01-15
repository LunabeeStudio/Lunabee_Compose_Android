package com.lunabee.lbcore.helper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class ShowDelayedLoadingTest {

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun rememberShowDelayedLoading_no_loading_test() {
        var actualShowLoading: Boolean? = null

        composeTestRule.setContent {
            val showLoading: Boolean by rememberShowDelayedLoading(
                shouldShowLoading = false,
                minLoadingShowDuration = Duration.ZERO,
                delayBeforeShow = Duration.ZERO,
            )

            actualShowLoading = showLoading
        }

        assertFalse(actualShowLoading!!)
    }

    @Test
    fun rememberShowDelayedLoading_show_no_delay_test() {
        var actualShowLoading: Boolean? = null

        composeTestRule.setContent {
            val showLoading: Boolean by rememberShowDelayedLoading(
                shouldShowLoading = true,
                minLoadingShowDuration = Duration.ZERO,
                delayBeforeShow = Duration.ZERO,
            )

            actualShowLoading = showLoading
        }

        assertTrue(actualShowLoading!!)
    }

    @Test
    fun rememberShowDelayedLoading_show_after_delay_test(): TestResult = runTest {
        var actualShowLoading: Boolean? = null
        val delayBeforeShow = 100.milliseconds

        composeTestRule.setContent {
            val showLoading: Boolean by rememberShowDelayedLoading(
                shouldShowLoading = true,
                minLoadingShowDuration = Duration.ZERO,
                delayBeforeShow = delayBeforeShow,
            )

            actualShowLoading = showLoading
        }

        assertFalse(actualShowLoading!!)
        composeTestRule.wait(delayBeforeShow * 1.5)
        assertTrue(actualShowLoading!!)
    }

    @Test
    fun rememberShowDelayedLoading_hide_no_delay_test(): TestResult = runTest {
        var actualShowLoading: Boolean? = null
        val shouldShowLoading = mutableStateOf(true)

        composeTestRule.setContent {
            val showLoading: Boolean by rememberShowDelayedLoading(
                shouldShowLoading = shouldShowLoading.value,
                minLoadingShowDuration = Duration.ZERO,
                delayBeforeShow = Duration.ZERO,
            )

            actualShowLoading = showLoading
        }

        assertTrue(actualShowLoading!!)
        shouldShowLoading.value = false
        composeTestRule.waitUntil { !actualShowLoading!! }
    }

    @Test
    fun rememberShowDelayedLoading_hide_min_loading_test(): TestResult = runTest {
        var actualShowLoading: Boolean? = null
        val shouldShowLoading = mutableStateOf(true)
        val minLoadingShowDuration = 100.milliseconds

        composeTestRule.setContent {
            val showLoading: Boolean by rememberShowDelayedLoading(
                shouldShowLoading = shouldShowLoading.value,
                minLoadingShowDuration = minLoadingShowDuration,
                delayBeforeShow = Duration.ZERO,
            )

            actualShowLoading = showLoading
        }

        // Immediately true
        assertTrue(actualShowLoading!!)

        // Request false
        shouldShowLoading.value = false
        composeTestRule.mainClock.advanceTimeByFrame()
        assertTrue(actualShowLoading!!) // Still true because of minLoadingShowDuration

        composeTestRule.wait(minLoadingShowDuration)
        assertFalse(actualShowLoading!!)
    }

    @Test
    fun rememberShowDelayedLoading_hide_before_delay_test(): TestResult = runTest {
        val actualShowLoading = mutableListOf<Boolean>()
        val shouldShowLoading = mutableStateOf(true)
        val delayBeforeShow = 200.milliseconds
        val minLoadingShowDuration = Duration.INFINITE

        composeTestRule.setContent {
            val showLoading: Boolean by rememberShowDelayedLoading(
                shouldShowLoading = shouldShowLoading.value,
                minLoadingShowDuration = minLoadingShowDuration,
                delayBeforeShow = delayBeforeShow,
            )

            actualShowLoading.add(showLoading)
        }

        // False because delay
        assertFalse(actualShowLoading.last())
        composeTestRule.wait(delayBeforeShow / 2)
        assertFalse(actualShowLoading.last())

        // Request false
        shouldShowLoading.value = false
        composeTestRule.mainClock.advanceTimeByFrame()
        assertFalse(actualShowLoading.last())

        // Assert showLoading was never true
        composeTestRule.wait(delayBeforeShow / 2)
        assertFalse(actualShowLoading.any { it })
    }
}

private suspend fun ComposeTestRule.wait(delay: Duration) {
    withContext(Dispatchers.IO) {
        delay(delay)
    }
    mainClock.advanceTimeByFrame()
}
