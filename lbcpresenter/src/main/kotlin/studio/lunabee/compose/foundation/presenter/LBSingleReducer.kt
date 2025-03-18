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
 * LBSingleReducer.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 12/16/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.presenter

/**
 * Subclass of [LBReducer] to implement single state reducer
 *
 * @param verbose enable verbose logs using kermit logger
 * @see LBReducer
 */
abstract class LBSingleReducer<UiState : PresenterUiState, NavScope, Action>(
    verbose: Boolean = false,
) : LBReducer<UiState, UiState, NavScope, Action, Action>(
    verbose = verbose,
) {
    final override fun filterAction(action: Action): Boolean = true
    final override fun filterUiState(actualState: UiState): Boolean = true
}
