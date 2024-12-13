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
 * MultiStatePresenter.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 12/12/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter.multistate

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import studio.lunabee.compose.foundation.presenter.LBPresenter
import studio.lunabee.compose.foundation.presenter.LBReducer
import javax.inject.Inject

@HiltViewModel
class MultiStatePresenter @Inject constructor() : LBPresenter<MultiStateUiState, MultiStateNavScope, MultiStateAction>() {

    val flow = flow<MultiStateAction> {
        while (true) {
            delay(2000)
            val actions = listOf<MultiStateAction>(
                MultiStateAction.ExampleAction,
                MultiStateAction.ExampleAllAction,
                MultiStateAction.ExampleErrorAction,
            )
            emit(actions.random())
        }
    }

    override val flows: List<Flow<MultiStateAction>> = listOf(flow)

    override fun getInitialState(): MultiStateUiState = MultiStateUiState.Data
    override fun initReducerByState(actualState: MultiStateUiState):
        LBReducer<MultiStateUiState, MultiStateUiState, MultiStateNavScope, MultiStateAction, MultiStateAction> {
        return when (actualState) {
            MultiStateUiState.Data -> MultiStateDataReducer(viewModelScope)
            MultiStateUiState.Error -> MultiStateErrorReducer(viewModelScope)
        } as LBReducer<MultiStateUiState, MultiStateUiState, MultiStateNavScope, MultiStateAction, MultiStateAction>
    }

    override val content: @Composable (MultiStateUiState) -> Unit = {
        val reducer = reducer.collectAsStateWithLifecycle()
        Column {
            Text("reducer: ${reducer.value.javaClass.simpleName}")
            when (it) {
                MultiStateUiState.Data -> Text("Data State")
                MultiStateUiState.Error -> Text("Error State")
            }
        }
    }
}
