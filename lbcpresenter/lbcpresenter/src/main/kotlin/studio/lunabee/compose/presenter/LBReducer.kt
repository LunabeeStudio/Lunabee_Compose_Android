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
 * LBReducer.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 3/19/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.presenter

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * @property verbose enable verbose logs using kermit logger
 */
abstract class LBReducer<UiState : MainUiState, MainUiState : PresenterUiState, NavScope, MainAction, Action : MainAction>(
    private val verbose: Boolean = false,
) {
    /**
     * CoroutineScope in witch side effects are executed.
     */
    abstract val coroutineScope: CoroutineScope

    /**
     * Emit a user action to the active reducer. Provided by the presenter
     */
    abstract val emitUserAction: (Action) -> Unit

    /**
     * Call each time an [Action] is collected by [collectReducer]
     * @param actualState the current [UiState] of the screen displayed
     * @param action the [Action] collected by [collectReducer]
     * @param performNavigation allows to access the [NavScope] to perform navigation
     * @return [ReduceResult] containing the new [UiState] and an optional SideEffect to execute right after the [UiState] update
     */
    abstract suspend fun reduce(
        actualState: UiState,
        action: Action,
        performNavigation: (NavScope.() -> Unit) -> Unit,
    ): ReduceResult<MainUiState>

    /**
     * Called by the presenter to create the uiStateFlow
     * merges all the actions [flows] provided by the presenter
     * @param actualState the current [UiState] of the screen displayed stored by the Presenter
     * @param performNavigation allows to access the [NavScope] to perform navigation
     */
    @Suppress("UNCHECKED_CAST")
    fun collectReducer(
        flows: List<Flow<MainAction>>,
        actualState: () -> MainUiState,
        performNavigation: (NavScope.() -> Unit) -> Unit,
    ): Flow<MainUiState> {
        val uiStateFlow = flows
            .merge()
            .filter { action ->
                filterAction(action) && filterUiState(actualState()).also {
                    log { "Filter (= $it) action <$action>" }
                }
            }.mapNotNull { action ->
                val state = actualState()
                log { "Reducing state <$state> with action <$action>" }
                (state as? UiState)?.let {
                    reduce(
                        actualState = it,
                        action = action as Action,
                        performNavigation = performNavigation,
                    ).also {
                        log { "Reduced state = ${it.uiState}" }
                    }
                }
            }.onEach { coroutineScope.launch { it.sideEffect?.invoke() } }
            .map { it.uiState }
        return if (verbose) {
            uiStateFlow.onCompletion { throwable -> log { "Reducer completed $throwable" } }
        } else {
            uiStateFlow
        }
    }

    /**
     * Filters action handled by the reduce function
     */
    abstract fun filterAction(
        action: MainAction,
    ): Boolean

    /**
     * Filters uiState handled by the reduce function
     */
    abstract fun filterUiState(
        actualState: MainUiState,
    ): Boolean

    private inline fun log(message: () -> String) {
        if (verbose) {
            Logger.withTag("LBPresenter").v(message())
        }
    }
}
