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
 * SimpleExemplePresenter.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 11/14/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter.simple

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import studio.lunabee.compose.foundation.presenter.LBPresenter
import studio.lunabee.compose.foundation.presenter.LBReducer
import javax.inject.Inject

@HiltViewModel
class SimpleExemplePresenter @Inject constructor() : LBPresenter<SimpleExempleUiState, SimpleExempleNavScope, SimpleExempleAction>() {

    override val flows: List<Flow<SimpleExempleAction>> = listOf()
    override fun getInitialState(): SimpleExempleUiState = SimpleExempleUiState(
        onToggleClick = { emitUserAction(SimpleExempleAction.NewCheckValue(it)) },
        onNewValue = { emitUserAction(SimpleExempleAction.NewValue) },
        isChecked = false,
        text = "Init",
    )

    override fun initReducerByState(actualState: SimpleExempleUiState):
        LBReducer<SimpleExempleUiState, SimpleExempleUiState, SimpleExempleNavScope, SimpleExempleAction, SimpleExempleAction> {
        return SimpleExempleReducer(viewModelScope)
    }

    override val content: @Composable (SimpleExempleUiState) -> Unit = { SimpleExempleScreen(it) }
}
