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
 * TestReducer.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/1/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import studio.lunabee.compose.foundation.presenter.LBReducer
import studio.lunabee.compose.foundation.presenter.ReduceResult
import studio.lunabee.compose.foundation.presenter.asResult
import studio.lunabee.compose.foundation.presenter.withSideEffect
import kotlin.random.Random

class TestReducer(
    private val performAction: (TestAction) -> Unit,
    override val coroutineScope: CoroutineScope,
) : LBReducer<TestUiState, TestNavScope, TestAction>() {
    override suspend fun reduce(
        actualState: TestUiState,
        action: TestAction,
        performNavigation: (TestNavScope.() -> Unit) -> Unit,
    ): ReduceResult<TestUiState> {
        return when (action) {
            TestAction.Finish -> {
                performNavigation { navigateBack() }
                actualState.asResult()
            }
            is TestAction.SetSubtitle -> actualState.copy(subtitle = action.subtitle).asResult()
            is TestAction.SetTitle -> actualState.copy(title = action.title).asResult()
            is TestAction.NewInt -> actualState.copy(timer = action.value).asResult()
            is TestAction.NewSuperValue -> actualState.copy(timer = action.value).asResult()
            is TestAction.Refresh -> actualState.copy(isRefreshing = action.value) withSideEffect if (action.value) {
                suspend {
                    delay(Random.nextLong(50, 1000))
                    performAction(TestAction.Refresh(false))
                }
            } else {
                null
            }
        }
    }
}
