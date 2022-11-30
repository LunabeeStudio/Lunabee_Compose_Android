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
 * AccessibilityState.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/10/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.accessibility.state

import android.content.Context
import android.view.accessibility.AccessibilityManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

/**
 * UI state that listens for accessibility change.
 *
 * @param accessibilityManager [AccessibilityManager] provided by Android system.
 */
@Stable
class AccessibilityState internal constructor(
    accessibilityManager: AccessibilityManager?,
) {
    var isAccessibilityEnabled: Boolean by mutableStateOf(value = accessibilityManager?.isEnabled ?: false)
        private set

    var isTouchExplorationEnabled: Boolean by mutableStateOf(
        value = accessibilityManager?.let { am -> am.isEnabled && am.isTouchExplorationEnabled } ?: false,
    )
        private set

    init {
        accessibilityManager?.addAccessibilityStateChangeListener { isAccessibilityEnabled ->
            if (this.isAccessibilityEnabled != isAccessibilityEnabled) {
                this.isAccessibilityEnabled = isAccessibilityEnabled
            }
            if (this.isTouchExplorationEnabled != isAccessibilityEnabled && isTouchExplorationEnabled) {
                this.isTouchExplorationEnabled = isAccessibilityEnabled && isTouchExplorationEnabled
            }
        }
    }
}

@Composable
fun rememberAccessibilityState(
    context: Context = LocalContext.current,
    accessibilityManager: AccessibilityManager? = remember {
        context.getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager
    },
): AccessibilityState {
    return remember {
        AccessibilityState(
            accessibilityManager = accessibilityManager,
        )
    }
}
