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
 * ModifierExt.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 7/19/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbctopappbar.material.extensions

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * Shortcut method to apply [asPaddingValues] method if requested.
 * @return [Modifier] with [asPaddingValues] applied or a default [Modifier]
 */
fun Modifier.statusBarsPadding() = composed {
    padding(paddingValues = WindowInsets.statusBars.asPaddingValues())
}
