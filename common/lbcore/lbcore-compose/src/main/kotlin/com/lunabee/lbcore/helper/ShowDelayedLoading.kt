/*
 * Copyright (c) 2026 Lunabee Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    minLoadingShowDuration: Duration = LBLoadingVisibilityDelayDelegate.DefaultMinLoadingShowDurationMs.milliseconds,
    delayBeforeShow: Duration = LBLoadingVisibilityDelayDelegate.DefaultDelayBeforeShowMs.milliseconds,
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
