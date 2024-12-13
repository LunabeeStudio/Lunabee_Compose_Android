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
 * SimpleExempleReducer.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 11/14/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter.simple

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.foundation.presenter.LBReducer
import studio.lunabee.compose.foundation.presenter.ReduceResult
import studio.lunabee.compose.foundation.presenter.asResult
import kotlin.random.Random

class SimpleExempleReducer(
    override val coroutineScope: CoroutineScope,
) : LBReducer<SimpleExempleUiState, SimpleExempleUiState, SimpleExempleNavScope, SimpleExempleAction, SimpleExempleAction>() {
    override suspend fun reduce(
        actualState: SimpleExempleUiState,
        action: SimpleExempleAction,
        performNavigation: (SimpleExempleNavScope.() -> Unit) -> Unit,
    ): ReduceResult<SimpleExempleUiState> {
        return when (action) {
            is SimpleExempleAction.NewCheckValue -> actualState.copy(isChecked = action.value).asResult()
            SimpleExempleAction.NewValue -> actualState.copy(text = Random.nextInt().toString()).asResult()
        }
    }

    override fun filterAction(action: SimpleExempleAction): Boolean {
        return true
    }
}
