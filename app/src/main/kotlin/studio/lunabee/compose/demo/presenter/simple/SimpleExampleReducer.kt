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
 * SimpleExampleReducer.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 2/5/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter.simple

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import kotlin.random.Random

class SimpleExampleReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (SimpleExampleAction) -> Unit,
) : LBSingleReducer<SimpleExampleUiState, SimpleExampleNavScope, SimpleExampleAction>(
        verbose = true,
    ) {
    override suspend fun reduce(
        actualState: SimpleExampleUiState,
        action: SimpleExampleAction,
        performNavigation: (SimpleExampleNavScope.() -> Unit) -> Unit,
    ): ReduceResult<SimpleExampleUiState> = when (action) {
        is SimpleExampleAction.NewCheckValue -> actualState.copy(isChecked = action.value).asResult()
        SimpleExampleAction.NewValue -> actualState.copy(text = Random.nextInt().toString()).asResult()
    }
}
