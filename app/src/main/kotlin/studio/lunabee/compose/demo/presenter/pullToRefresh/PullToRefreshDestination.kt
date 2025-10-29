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
 * PullToRefreshDestination.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 2/5/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter.pullToRefresh

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

data object PullToRefreshDestination {
    val route = PullToRefreshDestination.javaClass.simpleName

    fun composable(navGraphBuilder: NavGraphBuilder, navScope: PullToRefreshNavScope) {
        navGraphBuilder.composable(route) {
            val presenter: PullToRefreshPresenter = hiltViewModel()
            presenter(navScope)
        }
    }
}
