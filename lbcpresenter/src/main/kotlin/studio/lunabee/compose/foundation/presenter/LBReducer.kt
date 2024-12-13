/*
 * Copyright (c) 2024 Lunabee Studio
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
 * LBReducer.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/1/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class LBReducer<UiState : MainUiState, MainUiState : PresenterUiState, NavScope, MainAction, Action : MainAction> {
    abstract val coroutineScope: CoroutineScope

    abstract suspend fun reduce(
        actualState: UiState,
        action: Action,
        performNavigation: (NavScope.() -> Unit) -> Unit,
    ): ReduceResult<MainUiState>

    @Suppress("UNCHECKED_CAST")
    fun collectReducer(
        flows: List<Flow<MainAction>>,
        actualState: () -> MainUiState,
        performNavigation: (NavScope.() -> Unit) -> Unit,
    ): Flow<MainUiState> {
        return flows.merge().filter {
            filterAction(it)
        }.mapNotNull { action ->
            (actualState() as? UiState)?.let {
                reduce(
                    actualState = it,
                    action = action as Action,
                    performNavigation = performNavigation,
                )
            }
        }
            .onEach { coroutineScope.launch { it.sideEffect?.invoke() } }
            .map { it.uiState }
    }

    abstract fun filterAction(
        action: MainAction,
    ): Boolean
}
