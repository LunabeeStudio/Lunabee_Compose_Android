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
 * LbcTopAppBarUtils.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 7/17/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbctopappbar.material.internal

import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import studio.lunabee.compose.lbctopappbar.material.LbcMenuIconButton
import studio.lunabee.compose.lbctopappbar.material.extensions.statusBarsPadding
import studio.lunabee.compose.lbctopappbar.material.model.LbcTopAppBarAction

internal object LbcTopAppBarUtils {
    /**
     * Shortcut method to avoid repeating nullability check in each [androidx.compose.material.TopAppBar] of the lib.
     * @param lbcTopAppBarAction see [LbcTopAppBarAction]
     * @return a [LbcMenuIconButton] [Composable] if [lbcTopAppBarAction] is not null, null otherwise
     */
    internal fun getTopAppBarIconOrNull(
        lbcTopAppBarAction: LbcTopAppBarAction?,
    ): (@Composable () -> Unit)? {
        return if (lbcTopAppBarAction != null) {
            { LbcMenuIconButton(action = lbcTopAppBarAction) }
        } else {
            null
        }
    }

    /**
     * Shortcut method to apply [asPaddingValues] method if requested.
     * @param applyStatusBarPadding if set to true, will apply [asPaddingValues]
     * @return [Modifier] with [asPaddingValues] applied or a default [Modifier]
     */
    internal fun Modifier.applyStatusBarPaddingIfNeeded(
        applyStatusBarPadding: Boolean,
    ): Modifier {
        return if (applyStatusBarPadding) {
            statusBarsPadding()
        } else {
            this
        }
    }
}
