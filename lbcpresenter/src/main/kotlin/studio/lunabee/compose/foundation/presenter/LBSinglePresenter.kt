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
 * LBSinglePresenter.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 12/16/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.presenter

/**
 * Subclass of [LBPresenter] to implement a single state presenter
 */
abstract class LBSinglePresenter<UiState : PresenterUiState, NavScope : Any, Action> : LBPresenter<UiState, NavScope, Action>() {
    final override fun getReducerByState(
        actualState: UiState,
    ): LBSingleReducer<UiState, NavScope, Action> {
        return initReducer()
    }

    abstract fun initReducer(): LBSingleReducer<UiState, NavScope, Action>
}
