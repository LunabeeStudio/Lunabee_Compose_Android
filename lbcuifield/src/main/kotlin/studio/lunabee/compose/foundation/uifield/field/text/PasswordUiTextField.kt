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
 * PasswordUiTextField.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/22/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.field.text

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.UiField
import studio.lunabee.compose.foundation.uifield.UiFieldOption
import studio.lunabee.compose.foundation.uifield.field.UiFieldError
import studio.lunabee.compose.foundation.uifield.field.style.PasswordUiFieldData
import studio.lunabee.compose.foundation.uifield.field.style.PasswordUiFieldDataImpl
import studio.lunabee.compose.foundation.uifield.field.style.UiFieldStyleData
import studio.lunabee.compose.foundation.uifield.field.style.UiFieldStyleDataImpl
import studio.lunabee.compose.foundation.uifield.field.text.option.password.PasswordVisibilityFieldOption
import studio.lunabee.compose.foundation.uifield.field.text.option.password.PasswordVisibilityOptionData
import studio.lunabee.compose.foundation.uifield.field.text.option.password.PasswordVisibilityOptionHolder

class PasswordUiTextField(
    override var label: LbcTextSpec,
    override val initialValue: String,
    override var placeholder: LbcTextSpec,
    override val isFieldInError: (String) -> UiFieldError?,
    override val visibilityOptionData: PasswordVisibilityOptionData,
    override val id: String,
    override val savedStateHandle: SavedStateHandle,
    private val passwordUiFieldData: PasswordUiFieldData = PasswordUiFieldDataImpl(),
    override val onValueChange: (String) -> Unit = {},
    private val maxLine: Int = 1,
    private val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    private val onKeyboardActions: KeyboardActionHandler = KeyboardActionHandler { /* no-op */ },
) : UiField<String>(), PasswordVisibilityOptionHolder {
    private val mIsValueVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isValueVisible: StateFlow<Boolean> = mIsValueVisible.asStateFlow()

    override fun onVisibilityToggle() {
        mIsValueVisible.value = !isValueVisible.value
    }

    override val options: List<UiFieldOption> = listOf(
        PasswordVisibilityFieldOption(),
    )

    @Composable
    override fun Composable(modifier: Modifier) {
        val collectedValue by mValue.collectAsState()
        val collectedIsValueVisible by isValueVisible.collectAsState()
        val collectedError by error.collectAsState()

        passwordUiFieldData.ComposeTextField(
            value = collectedValue,
            onValueChange = { value = it },
            modifier = modifier.onFocusEvent {
                if (!it.hasFocus && hasBeenFocused) {
                    checkAndDisplayError()
                } else {
                    hasBeenFocused = true
                    dismissError()
                }
            },
            placeholder = placeholder,
            label = label,
            trailingIcon = if (options.isNotEmpty()) {
                { options.forEach { it.Composable(modifier = Modifier) } }
            } else {
                null
            },
            isValueVisible = collectedIsValueVisible,
            keyboardOptions = keyboardOptions,
            onKeyboardActions = onKeyboardActions,
            maxLine = maxLine,
            readOnly = false,
            error = collectedError,
            interactionSource = null,
        )
    }

    // Not used here ...
    override val uiFieldStyleData: UiFieldStyleData = UiFieldStyleDataImpl()
    override fun savedValueToData(value: String): String {
        return value
    }

    override fun valueToSavedString(value: String): String {
        return value
    }

    override fun valueToDisplayedString(value: String): String {
        return value
    }
}
