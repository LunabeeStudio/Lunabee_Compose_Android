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
 * TestPresenter.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/1/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import studio.lunabee.compose.foundation.presenter.LBPresenter
import studio.lunabee.compose.foundation.presenter.LBReducer
import javax.inject.Inject
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

data class TestUiState(
    val title: String,
    val subtitle: String,
    val timer: Int,
    val onBackClick: () -> Unit,
    val onTitleChange: () -> Unit,
)

data class TestNavScope(
    val navigateBack: () -> Unit
)

sealed interface TestAction {
    data object Finish : TestAction
    data class SetTitle(val title: String) : TestAction
    data class SetSubtitle(val subtitle: String) : TestAction
    data class NewInt(val value: Int) : TestAction
    data class NewSuperValue(val value: Int) : TestAction
}

@HiltViewModel
class TestPresenter @Inject constructor() : LBPresenter<TestUiState, TestNavScope, TestAction>() {

    private val collectFromDataBase: Flow<TestAction> = flow {
        while (true) {
            delay(1.seconds)
            println("collectFromDataBase")
            emit(Random.nextInt())
        }
    }.map { TestAction.NewInt(it) }

    private val collectFromDataBase2: Flow<TestAction> = flow {
        while (true) {
            delay(2.seconds)
            emit(Random.nextInt())
        }
    }.map { TestAction.NewSuperValue(it) }

    private val userActionFlow: MutableSharedFlow<TestAction> = MutableSharedFlow()

    override val flows: List<Flow<TestAction>> = listOf(collectFromDataBase, collectFromDataBase2, userActionFlow)

    override fun getInitialState(): TestUiState = TestUiState(
        title = "Title",
        subtitle = "Subtitle",
        onBackClick = { viewModelScope.launch { userActionFlow.emit(TestAction.Finish) } },
        timer = 0,
        onTitleChange = { viewModelScope.launch { userActionFlow.emit(TestAction.SetTitle("New Title")) } }
    )

    override val reducer: LBReducer<TestUiState, TestNavScope, TestAction> = TestReducer()

    override val content: @Composable (TestUiState) -> Unit = { uiState ->
        TestScreen(uiState)
    }
}