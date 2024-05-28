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
 * VisibilityOptionHolder.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/24/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.field.text.option

import androidx.compose.ui.text.input.VisualTransformation
import kotlinx.coroutines.flow.StateFlow

interface VisibilityOptionHolder {
    val visualTransformation: StateFlow<VisualTransformation>
    val visibilityOptionData: VisibilityOptionData

    fun onVisibilityToggle()
}