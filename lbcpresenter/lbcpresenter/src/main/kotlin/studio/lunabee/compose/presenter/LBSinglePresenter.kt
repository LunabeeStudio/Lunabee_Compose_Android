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
 * LBSinglePresenter.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 3/19/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.presenter

/**
 * Subclass of [LBPresenter] to implement a single state presenter
 *
 * @param verbose enable verbose logs using kermit logger
 * @see LBPresenter
 */
abstract class LBSinglePresenter<UiState : PresenterUiState, NavScope : Any, Action>(
    verbose: Boolean = false,
) : LBPresenter<UiState, NavScope, Action>(
    verbose = verbose,
) {
    final override fun getReducerByState(
        actualState: UiState,
    ): LBSingleReducer<UiState, NavScope, Action> {
        return initReducer()
    }

    abstract fun initReducer(): LBSingleReducer<UiState, NavScope, Action>
}
