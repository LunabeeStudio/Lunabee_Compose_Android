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
 * PullToRefreshPresenter.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 2/5/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter.pullToRefresh

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import studio.lunabee.compose.foundation.presenter.LBSinglePresenter
import studio.lunabee.compose.foundation.presenter.LBSingleReducer
import javax.inject.Inject

@HiltViewModel
class PullToRefreshPresenter @Inject constructor() :
    LBSinglePresenter<PullToRefreshUiState, PullToRefreshNavScope, PullToRefreshAction>() {

    override val flows: List<Flow<PullToRefreshAction>> = listOf()
    override fun getInitialState(): PullToRefreshUiState = PullToRefreshUiState(
        isRefreshing = false,
        refresh = { emitUserAction(PullToRefreshAction.Refresh) },
    )

    override fun initReducer(): LBSingleReducer<PullToRefreshUiState, PullToRefreshNavScope, PullToRefreshAction> =
        PullToRefreshReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction,
        )

    override val content: @Composable (PullToRefreshUiState) -> Unit = { PullToRefreshScreen(it) }
}
