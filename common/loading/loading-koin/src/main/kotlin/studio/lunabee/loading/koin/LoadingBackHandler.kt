/*
 * Copyright (c) 2026 Lunabee Studio
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
 */

package studio.lunabee.loading.koin

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject

/**
 * Wrapper around [BackHandler] compliant with the global loading view by using the [LoadingManager]
 * @see BackHandler
 *
 * @param enabled if this BackHandler should be enabled when there is no loading
 * @param onBack the action invoked by pressing the system back when there is no loading
 */
@Composable
fun LoadingBackHandler(
    enabled: Boolean = true,
    onBack: () -> Unit,
) {
    studio.lunabee.loading.LoadingBackHandler(
        loadingManager = koinInject(),
        enabled = enabled,
        onBack = onBack,
    )
}
