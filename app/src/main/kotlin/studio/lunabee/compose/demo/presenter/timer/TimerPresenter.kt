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
 * TimerPresenter.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 2/5/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter.timer

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import studio.lunabee.compose.foundation.presenter.LBSinglePresenter
import studio.lunabee.compose.foundation.presenter.LBSingleReducer
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class TimerPresenter @Inject constructor() : LBSinglePresenter<TimerUiState, TimerNavScope, TimerAction>(
    verbose = true,
) {
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

    override fun initReducer(): LBSingleReducer<TimerUiState, TimerNavScope, TimerAction> {
        return TimerReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction,
        )
    }

    override val content: @Composable (TimerUiState) -> Unit = { TimerScreen(it) }
}
