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

package com.lunabee.lbloading

import com.lunabee.lbcore.helper.LBLoadingVisibilityDelayDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * [LoadingManager] which use [LBLoadingVisibilityDelayDelegate] to avoid loading blinks
 *
 * @property loadingDelegate The delay handling delegate
 */
class DelayedLoadingManager(
    private val loadingDelegate: LBLoadingVisibilityDelayDelegate,
) : LoadingManager() {
    private val _loadingState: MutableStateFlow<GlobalLoadingState> = MutableStateFlow(GlobalLoadingState.None)
    override val loadingState: StateFlow<GlobalLoadingState> = _loadingState

    override fun startLoading() {
        _loadingState.value = GlobalLoadingState.Blocking
        loadingDelegate.delayShowLoading {
            _loadingState.value = GlobalLoadingState.Loading
        }
    }

    override fun stopLoading() {
        loadingDelegate.delayHideLoading {
            _loadingState.value = GlobalLoadingState.None
        }
    }

    override fun startBlocking() {
        if (!loadingState.value.isBlocking && !loadingDelegate.isActive()) {
            _loadingState.value = GlobalLoadingState.Blocking
        }
    }

    override fun stopBlocking() {
        // Keep loading state if set
        if (!loadingState.value.isLoading && !loadingDelegate.isActive()) {
            _loadingState.value = GlobalLoadingState.None
        }
    }
}
