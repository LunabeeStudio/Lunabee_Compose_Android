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
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.field.UiFieldError
import studio.lunabee.compose.foundation.uifield.field.style.UiFieldStyleData

abstract class UiField<T> {
    abstract val id: String
    abstract val savedStateHandle: SavedStateHandle
    abstract val initialValue: T
    abstract var placeholder: LbcTextSpec?
    abstract var label: LbcTextSpec?
    abstract val options: List<UiFieldOption>
    abstract val uiFieldStyleData: UiFieldStyleData
    abstract val isFieldInError: (T) -> UiFieldError?
    abstract val onValueChange: (T) -> Unit
    abstract val readOnly: Boolean
    abstract val enabled: Boolean

    protected val mValue: MutableStateFlow<T> by lazy {
        MutableStateFlow(
            savedStateHandle.get<String>(id)?.let(::savedValueToData) ?: initialValue
        )
    }

    val isInError: Flow<Boolean> by lazy { mValue.map { isFieldInError(it) != null } }

    protected val error: MutableStateFlow<UiFieldError?> = MutableStateFlow(null)

    /**
     * Focus has been acquired by the field at least once
     */
    protected var hasBeenFocused: Boolean = false

    var value: T
        get() = mValue.value
        set(value) {
            savedStateHandle.set(key = id, valueToSavedString(value))
            mValue.value = value
            onValueChange(value)
        }

    fun checkAndDisplayError(): Boolean {
        val error = isFieldInError(value)
        error?.let(::displayError)
        return error != null
    }

    fun dismissError() {
        error.value = null
    }

    fun displayError(displayError: UiFieldError) {
        error.value = displayError
    }

    abstract fun valueToDisplayedString(value: T): String

    abstract fun valueToSavedString(value: T): String

    abstract fun savedValueToData(value: String): T

    @Composable
    abstract fun Composable(modifier: Modifier)
}
