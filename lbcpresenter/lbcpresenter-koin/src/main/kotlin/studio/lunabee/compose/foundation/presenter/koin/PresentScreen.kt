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
 * PresentScreen.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 3/19/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.presenter.koin

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import studio.lunabee.compose.foundation.presenter.LBPresenter

/**
 * Inject presenter as viewmodel and initialize it.
 */
@Composable
inline fun <NavScope, reified Presenter : LBPresenter<*, NavScope, *>> PresentScreen(navScope: NavScope) {
    val presenter: Presenter = koinViewModel()
    presenter.invoke(navScope)
}
