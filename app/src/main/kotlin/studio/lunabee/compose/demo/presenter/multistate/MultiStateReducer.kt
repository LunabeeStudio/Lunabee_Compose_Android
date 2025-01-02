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
 * MultiStateReducer.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 12/12/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter.multistate

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.foundation.presenter.LBReducer
import studio.lunabee.compose.foundation.presenter.ReduceResult
import studio.lunabee.compose.foundation.presenter.asResult

abstract class MultiStateReducer<State : MultiStateUiState, MultiStateNavScope, Action : MultiStateAction> :
    LBReducer<State, MultiStateUiState, MultiStateNavScope, MultiStateAction, Action>()

class MultiStateDataReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (MultiStateAction.DataAction) -> Unit,
) : MultiStateReducer<MultiStateUiState.Data, MultiStateNavScope, MultiStateAction.DataAction>() {
    override suspend fun reduce(
        actualState: MultiStateUiState.Data,
        action: MultiStateAction.DataAction,
        performNavigation: (MultiStateNavScope.() -> Unit) -> Unit,
    ): ReduceResult<MultiStateUiState> {
        return when (action) {
            MultiStateAction.ExampleAction -> MultiStateUiState.Error(this::class.simpleName!!).asResult()
            MultiStateAction.ExampleAllAction -> actualState.copy(reducer = this::class.simpleName!!).asResult()
        }
    }

    override fun filterAction(action: MultiStateAction): Boolean {
        return action is MultiStateAction.DataAction
    }

    override fun filterUiState(actualState: MultiStateUiState): Boolean {
        return actualState is MultiStateUiState.Data
    }
}

class MultiStateErrorReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (MultiStateAction.ErrorAction) -> Unit,
) : MultiStateReducer<MultiStateUiState.Error, MultiStateNavScope, MultiStateAction.ErrorAction>() {
    override suspend fun reduce(
        actualState: MultiStateUiState.Error,
        action: MultiStateAction.ErrorAction,
        performNavigation: (MultiStateNavScope.() -> Unit) -> Unit,
    ): ReduceResult<MultiStateUiState> {
        return when (action) {
            MultiStateAction.ExampleAllAction -> MultiStateUiState.Data(this::class.simpleName!!).asResult()
            MultiStateAction.ExampleErrorAction -> actualState.copy(reducer = this::class.simpleName!!).asResult()
        }
    }

    override fun filterAction(action: MultiStateAction): Boolean {
        return action is MultiStateAction.ErrorAction
    }

    override fun filterUiState(actualState: MultiStateUiState): Boolean {
        return actualState is MultiStateUiState.Error
    }
}
