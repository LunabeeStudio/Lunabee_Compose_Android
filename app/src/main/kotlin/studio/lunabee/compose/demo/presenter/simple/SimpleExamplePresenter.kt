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
 * SimpleExamplePresenter.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 2/5/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter.simple

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import studio.lunabee.compose.foundation.presenter.LBSinglePresenter
import studio.lunabee.compose.foundation.presenter.LBSingleReducer
import javax.inject.Inject

@HiltViewModel
class SimpleExamplePresenter @Inject constructor() : LBSinglePresenter<SimpleExampleUiState, SimpleExampleNavScope, SimpleExampleAction>(
    verbose = true,
) {
    override val flows: List<Flow<SimpleExampleAction>> = listOf()

    override fun getInitialState(): SimpleExampleUiState = SimpleExampleUiState(
        onToggleClick = { emitUserAction(SimpleExampleAction.NewCheckValue(it)) },
        onNewValue = { emitUserAction(SimpleExampleAction.NewValue) },
        isChecked = false,
        text = "Init",
    )

    override fun initReducer(): LBSingleReducer<SimpleExampleUiState, SimpleExampleNavScope, SimpleExampleAction> {
        return SimpleExampleReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction,
        )
    }

    override val content: @Composable (SimpleExampleUiState) -> Unit = { SimpleExempleScreen(it) }
}
