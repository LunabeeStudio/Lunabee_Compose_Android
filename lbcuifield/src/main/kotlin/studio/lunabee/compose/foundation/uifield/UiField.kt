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
 * UiField.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/22/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.field.UiFieldError
import studio.lunabee.compose.foundation.uifield.field.data.UiFieldData

abstract class UiField<T> {
    abstract val initialValue: T
    abstract var placeholder: LbcTextSpec
    abstract var label: LbcTextSpec
    abstract val options: List<UiFieldOption>
    abstract val uiFieldData: UiFieldData
    abstract val isFieldInError: (T) -> UiFieldError?

    protected val value: MutableStateFlow<T> by lazy { MutableStateFlow(initialValue) }

    protected val error: MutableStateFlow<UiFieldError?> = MutableStateFlow(null)

    fun getValue(): T {
        return value.value
    }

    fun checkAndDisplayError() {
        error.value = isFieldInError(getValue())
    }

    abstract fun valueToString(value: T): String

    @Composable
    abstract fun Composable(modifier: Modifier)
}
