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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

abstract class LBPresenter<UiState : PresenterUiState, NavScope : Any, Action> : ViewModel() {

    open val userActionFlow: MutableSharedFlow<Action> = MutableSharedFlow(extraBufferCapacity = 10)
    abstract val flows: List<Flow<Action>>

    abstract fun getInitialState(): UiState

    abstract val reducer: LBReducer<UiState, NavScope, Action>

    private val navigation: MutableStateFlow<(NavScope.() -> Unit)?> = MutableStateFlow(null)

    private fun consumeNavigation() {
        navigation.value = null
    }

    private fun performNavigation(navigateAction: NavScope.() -> Unit) {
        navigation.value = navigateAction
    }

    abstract val content: @Composable (UiState) -> Unit

    private val uiStateFlow: StateFlow<UiState> by lazy {
        var actualStateSaved: UiState = getInitialState()
        reducer.collectReducer(
            flows = flows + userActionFlow,
            actualState = { actualStateSaved },
            performNavigation = ::performNavigation,
        ).onEach {
            actualStateSaved = it
        }.stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(1_000), actualStateSaved)
    }

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

@Composable
inline fun <NavScope, reified Presenter : LBPresenter<*, NavScope, *>> PresentScreen(navScope: NavScope) {
    val presenter: Presenter = hiltViewModel()
    presenter.invoke(navScope)
}
