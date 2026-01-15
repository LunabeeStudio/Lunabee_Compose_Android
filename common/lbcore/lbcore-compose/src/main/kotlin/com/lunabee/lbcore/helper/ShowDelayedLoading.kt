package com.lunabee.lbcore.helper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Helper composable to show/hide loading with a delay to avoid blink on tiny loading time
 *
 * @param shouldShowLoading Condition to show the loading
 * @param shouldHideLoading Condition to hide the loading
 * @param minLoadingShowDuration Minimum duration of showing the loading
 * @param delayBeforeShow Delay to reach before showing the loading
 *
 * @see [LBLoadingVisibilityDelayDelegate]
 */
@Composable
@Stable
fun rememberShowDelayedLoading(
    shouldShowLoading: Boolean,
    shouldHideLoading: Boolean = !shouldShowLoading,
    minLoadingShowDuration: Duration = LBLoadingVisibilityDelayDelegate.DEFAULT_MIN_LOADING_SHOW_DURATION_MS.milliseconds,
    delayBeforeShow: Duration = LBLoadingVisibilityDelayDelegate.DEFAULT_DELAY_BEFORE_SHOW_MS.milliseconds,
): State<Boolean> {
    val loadingDelegate = remember(minLoadingShowDuration, delayBeforeShow) {
        LBLoadingVisibilityDelayDelegate(
            minLoadingShowDuration = minLoadingShowDuration,
            delayBeforeShow = delayBeforeShow,
        )
    }

    val showDelayedLoading = remember {
        mutableStateOf(false)
    }

    if (shouldShowLoading) {
        loadingDelegate.delayShowLoading {
            showDelayedLoading.value = true
        }
    }

    if (shouldHideLoading) {
        loadingDelegate.delayHideLoading {
            showDelayedLoading.value = false
        }
    }

    return showDelayedLoading
}
