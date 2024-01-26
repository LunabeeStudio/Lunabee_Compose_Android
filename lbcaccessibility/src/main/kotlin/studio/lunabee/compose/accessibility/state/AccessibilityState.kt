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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

/**
 * This state is based on [AccessibilityManager] (https://developer.android.com/reference/android/view/accessibility/AccessibilityManager).
 */
@Stable
class AccessibilityState internal constructor(
    accessibilityManager: AccessibilityManager?,
) {
    private var _isAccessibilityEnabled: Boolean by mutableStateOf(value = accessibilityManager?.isEnabled ?: false)

    private var _isTouchExplorationEnabled: Boolean by mutableStateOf(
        value = accessibilityManager?.let { am -> am.isEnabled && am.isTouchExplorationEnabled } ?: false,
    )

    /**
     * This value is `true` if at least one accessibility parameter is enabled.
     * Avoid changing UI or app behavior based on the state of accessibility.
     */
    val isAccessibilityEnabled: Boolean by derivedStateOf { _isAccessibilityEnabled }

    /**
     * This value is `true` when Talkback (or equivalent) is activated. Even if it should be limited, you can use this
     * flag to change UI or app behavior if you don't avec any other options.
     */
    val isTouchExplorationEnabled: Boolean by derivedStateOf { _isTouchExplorationEnabled }

    init {
        accessibilityManager?.apply {
            addAccessibilityStateChangeListener { isAccessibilityEnabled -> _isAccessibilityEnabled = isAccessibilityEnabled }
            addTouchExplorationStateChangeListener { isTouchExplorationEnabled -> _isTouchExplorationEnabled = isTouchExplorationEnabled }
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
