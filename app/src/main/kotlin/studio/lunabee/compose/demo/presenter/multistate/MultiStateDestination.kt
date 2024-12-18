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
 * MultiStateDestination.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 12/12/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter.multistate

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

data object MultiStateDestination {
    const val route = "MultiStateDestination"

    fun composable(navGraphBuilder: NavGraphBuilder, navScope: MultiStateNavScope) {
        navGraphBuilder.composable(route) {
            val presenter: MultiStatePresenter = hiltViewModel()
            presenter(navScope)
        }
    }
}
