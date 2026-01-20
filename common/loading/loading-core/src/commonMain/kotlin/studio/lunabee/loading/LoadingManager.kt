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

package studio.lunabee.loading

import com.lunabee.lbcore.model.LBFlowResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.yield

enum class GlobalLoadingState(val isBlocking: Boolean, val isLoading: Boolean) {
    None(false, false),
    Blocking(true, false),
    Loading(true, true),
}

/**
 * Manage global loading by providing a compose [State] to show and hide the global loading
 */
abstract class LoadingManager {
    abstract val loadingState: StateFlow<GlobalLoadingState>

    /**
     * Request show loading
     */
    abstract fun startLoading()

    /**
     * Request hide loading
     */
    abstract fun stopLoading()

    /**
     * Request block interactions
     */
    abstract fun startBlocking()

    /**
     * Request unblock interactions
     */
    abstract fun stopBlocking()

    /**
     * Safely run [block] with global loading enable. Call [yield] immediately after starting loading to allow UI refresh asap.
     */
    suspend inline fun <T> withLoading(block: () -> T): T {
        startLoading()
        return try {
            yield()
            block()
        } finally {
            stopLoading()
        }
    }

    /**
     * Safely run [block] with global blocking enable. Call [yield] immediately after starting blocking to allow UI refresh asap.
     */
    suspend inline fun <T> withBlocking(block: () -> T): T {
        startBlocking()
        return try {
            yield()
            block()
        } finally {
            stopBlocking()
        }
    }
}

/**
 * Bind the [LoadingManager] to a flow of [LBFlowResult] to show loading
 */
fun <T> Flow<LBFlowResult<T>>.withLoading(
    loadingManager: LoadingManager,
): Flow<LBFlowResult<T>> =
    onEach {
        if (it is LBFlowResult.Loading && !loadingManager.loadingState.value.isBlocking) {
            loadingManager.startLoading()
            yield()
        }
    }.onCompletion {
        loadingManager.stopLoading()
    }

/**
 * Bind the [LoadingManager] to a flow of [LBFlowResult] to block UI
 */
fun <T> Flow<LBFlowResult<T>>.withBlocking(
    loadingManager: LoadingManager,
): Flow<LBFlowResult<T>> =
    onEach {
        if (it is LBFlowResult.Loading) {
            loadingManager.startBlocking()
            yield()
        }
    }.onCompletion {
        loadingManager.stopBlocking()
    }
