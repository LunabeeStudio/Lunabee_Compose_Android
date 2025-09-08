/*
 * Copyright (c) 2025 Lunabee Studio
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
 *
 * PullToRefreshReducer.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 2/5/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter.pullToRefresh

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect

class PullToRefreshReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (PullToRefreshAction) -> Unit
) : LBSingleReducer<PullToRefreshUiState, PullToRefreshNavScope, PullToRefreshAction>(
    verbose = true
) {
    override suspend fun reduce(
        actualState: PullToRefreshUiState,
        action: PullToRefreshAction,
        performNavigation: (PullToRefreshNavScope.() -> Unit) -> Unit
    ): ReduceResult<PullToRefreshUiState> =
        when (action) {
            PullToRefreshAction.Refresh ->
                actualState.copy(isRefreshing = true) withSideEffect {
                    delay(1000) // Simulate loading
                    emitUserAction(PullToRefreshAction.StopRefresh)
                }
            PullToRefreshAction.StopRefresh -> actualState.copy(isRefreshing = false).asResult()
        }
}
