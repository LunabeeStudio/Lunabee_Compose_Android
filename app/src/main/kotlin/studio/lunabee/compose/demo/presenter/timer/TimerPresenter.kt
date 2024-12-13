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
 * TimerPresenter.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 11/14/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter.timer

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import studio.lunabee.compose.foundation.presenter.LBPresenter
import studio.lunabee.compose.foundation.presenter.LBReducer
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class TimerPresenter @Inject constructor() : LBPresenter<TimerUiState, TimerNavScope, TimerAction>() {
    private val timerFlow: Flow<TimerAction.NewTimerValue> = flow {
        var value: Int = 0
        while (true) {
            delay(1.seconds)
            emit(value++)
        }
    }.map { TimerAction.NewTimerValue(it) }

    override val flows: List<Flow<TimerAction>> = listOf(timerFlow)
    override fun getInitialState(): TimerUiState = TimerUiState(
        timer = 0,
    )

    override fun initReducerByState(actualState: TimerUiState):
        LBReducer<TimerUiState, TimerUiState, TimerNavScope, TimerAction, TimerAction> {
        return TimerReducer(viewModelScope)
    }

    override val content: @Composable (TimerUiState) -> Unit = { TimerScreen(it) }
}
