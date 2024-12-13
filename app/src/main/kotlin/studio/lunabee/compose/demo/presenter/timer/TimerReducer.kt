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
 * TimerReducer.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 11/14/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter.timer

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.foundation.presenter.LBReducer
import studio.lunabee.compose.foundation.presenter.ReduceResult
import studio.lunabee.compose.foundation.presenter.asResult

class TimerReducer(
    override val coroutineScope: CoroutineScope,
) : LBReducer<TimerUiState, TimerUiState, TimerNavScope, TimerAction, TimerAction>() {
    override suspend fun reduce(
        actualState: TimerUiState,
        action: TimerAction,
        performNavigation: (TimerNavScope.() -> Unit) -> Unit,
    ): ReduceResult<TimerUiState> {
        return when (action) {
            is TimerAction.NewTimerValue -> actualState.copy(timer = action.timerValue).asResult()
        }
    }

    override fun filterAction(action: TimerAction): Boolean {
        return true
    }
}
