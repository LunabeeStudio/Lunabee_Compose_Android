/*
 * Copyright © 2022 Lunabee Studio
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
 * TopAppBarState.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 7/18/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.material.topappbar.state

import androidx.compose.material.AppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
class TopAppBarState(
    isElevationEnabled: Boolean,
    isStatusBarPaddingEnabled: Boolean,
    isLoading: Boolean,
    isMenuShowingWhenLoading: Boolean,
    isNavigationEnabled: Boolean,
) {
    var isElevationEnabled: Boolean by mutableStateOf(value = isElevationEnabled)
    var isStatusBarPaddingEnabled: Boolean by mutableStateOf(value = isStatusBarPaddingEnabled)
    var isLoading: Boolean by mutableStateOf(value = isLoading)
    var isMenuShowingWhenLoading: Boolean by mutableStateOf(value = isMenuShowingWhenLoading)
    var isNavigationEnabled: Boolean by mutableStateOf(value = isNavigationEnabled)

    val currentElevation: Dp
        get() = if (isElevationEnabled) AppBarDefaults.TopAppBarElevation else 0.dp

    companion object {
        val Saver: Saver<TopAppBarState, Any> = run {
            val isElevationEnabledKey = "isElevationEnabledKey"
            val isStatusBarPaddingEnabledKey = "isStatusBarPaddingEnabledKey"
            val isLoadingKey = "isLoadingKey"
            val isMenuShowingWhenLoadingKey = "isMenuShowingWhenLoadingKey"
            val isNavigationEnabledKey = "isNavigationEnabledKey"

            mapSaver(
                save = { state ->
                    mapOf(
                        isElevationEnabledKey to state.isElevationEnabled,
                        isStatusBarPaddingEnabledKey to state.isStatusBarPaddingEnabled,
                        isLoadingKey to state.isLoading,
                        isMenuShowingWhenLoadingKey to state.isMenuShowingWhenLoading,
                        isNavigationEnabledKey to state.isNavigationEnabled,
                    )
                },
                restore = { restoredMap ->
                    TopAppBarState(
                        isElevationEnabled = restoredMap[isElevationEnabledKey] as Boolean,
                        isStatusBarPaddingEnabled = restoredMap[isStatusBarPaddingEnabledKey] as Boolean,
                        isLoading = restoredMap[isLoadingKey] as Boolean,
                        isMenuShowingWhenLoading = restoredMap[isMenuShowingWhenLoadingKey] as Boolean,
                        isNavigationEnabled = restoredMap[isNavigationEnabledKey] as Boolean,
                    )
                },
            )
        }
    }
}

@Composable
fun rememberTopAppBarState(
    isElevationEnabled: Boolean = false,
    isStatusBarPaddingEnabled: Boolean = false,
    isLoading: Boolean = false,
    isMenuShowingWhenLoading: Boolean = false,
    isNavigationEnabled: Boolean = false,
): TopAppBarState {
    return rememberSaveable(saver = TopAppBarState.Saver) {
        TopAppBarState(
            isElevationEnabled = isElevationEnabled,
            isStatusBarPaddingEnabled = isStatusBarPaddingEnabled,
            isLoading = isLoading,
            isMenuShowingWhenLoading = isMenuShowingWhenLoading,
            isNavigationEnabled = isNavigationEnabled,
        )
    }
}
