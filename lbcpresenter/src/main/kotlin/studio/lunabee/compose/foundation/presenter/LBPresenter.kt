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
 * LBPresenter.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/1/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.seconds

/**
 * Typealias to represent a simplify reducer declaration in presenter.
 */
typealias LBSimpleReducer<UiState, NavScope, Action> = LBReducer<out UiState, UiState, NavScope, Action, out Action>

abstract class LBPresenter<UiState : PresenterUiState, NavScope : Any, Action> : ViewModel() {

    /**
     * Channel to send user actions to the active reducer.
     */
    private val userActionChannel: Channel<Action> = Channel(Channel.UNLIMITED)

    /**
     * List of action flows merged and collected by the reducer in addition of the [userActionChannel].
     */
    abstract val flows: List<Flow<Action>>

    /**
     * Initial UiState displayed when the presenter is created.
     */
    abstract fun getInitialState(): UiState

    /**
     * Called every time the [UiState] class changes.
     * @param actualState the newly displayed [UiState]
     * @return the new reducer corresponding the [actualState]
     */
    abstract fun getReducerByState(
        actualState: UiState,
    ): LBSimpleReducer<UiState, NavScope, Action>

    private val navigation: MutableStateFlow<(NavScope.() -> Unit)?> = MutableStateFlow(null)

    private fun consumeNavigation() {
        navigation.value = null
    }

    private fun performNavigation(navigateAction: NavScope.() -> Unit) {
        navigation.value = navigateAction
    }

    /**
     * Emit a user action to the active reducer via [userActionChannel].
     */
    fun emitUserAction(action: Action) {
        userActionChannel.trySend(action)
    }

    /**
     * Content of the screen handled by the presenter with the current [UiState].
     */
    abstract val content: @Composable (UiState) -> Unit

    /**
     * StateFlow of the current [UiState] collected in the the [invoke] composable.
     * Handles the reducer change automatically
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private val uiStateFlow: StateFlow<UiState> by lazy {
        val reducer: MutableStateFlow<LBSimpleReducer<UiState, NavScope, Action>> = MutableStateFlow(
            getReducerByState(actualState = getInitialState()),
        )

        var actualStateSaved: UiState = getInitialState()
        reducer
            .flatMapLatest {
                it.collectReducer(
                    flows = flows + userActionChannel.receiveAsFlow(),
                    actualState = { actualStateSaved },
                    performNavigation = ::performNavigation,
                ).onEach { state ->
                    if (actualStateSaved::class != state::class) {
                        reducer.emit(getReducerByState(actualState = state))
                    }
                    actualStateSaved = state
                    if (actualStateSaved::class != state::class) {
                        delay(5.seconds)
                    }
                }
            }
            .stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(5_000), actualStateSaved)
    }

    /**
     * Function called to initialize the presenter and display the screen content.
     * Handles navigation calls and consumes them automatically
     * Collect the [uiStateFlow] to display the screen content.
     */
    @Composable
    operator fun invoke(navScope: NavScope) {
        val navigation by navigation.collectAsStateWithLifecycle()
        navigation?.let {
            LaunchedEffect(navigation) {
                it(navScope)
                consumeNavigation()
            }
        }
        val uiState by uiStateFlow.collectAsStateWithLifecycle()
        content(uiState)
    }
}
