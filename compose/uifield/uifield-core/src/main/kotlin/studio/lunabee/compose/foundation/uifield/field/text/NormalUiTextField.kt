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

package studio.lunabee.compose.foundation.uifield.field.text

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.UiFieldOption
import studio.lunabee.compose.foundation.uifield.field.UiFieldError
import studio.lunabee.compose.foundation.uifield.field.style.DefaultUiFieldStyleData
import studio.lunabee.compose.foundation.uifield.field.style.UiFieldStyleData

class NormalUiTextField(
    override val initialValue: String = "",
    override val label: LbcTextSpec?,
    override val placeholder: LbcTextSpec?,
    override val isFieldInError: (String) -> UiFieldError?,
    override val id: String,
    override val savedStateHandle: SavedStateHandle,
    override val options: List<UiFieldOption> = listOf(),
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
    override val uiFieldStyleData: UiFieldStyleData = DefaultUiFieldStyleData(),
    val maxLine: Int = 1,
    override val readOnly: Boolean = false,
    override val enabled: Boolean = true,
    val visualTransformation: StateFlow<VisualTransformation> = MutableStateFlow(
        VisualTransformation.None,
    ).asStateFlow(),
    override val onValueChange: (String) -> Unit = {},
) : TextUiField<String>() {
    override fun valueToDisplayedString(value: String): String = value

    override fun valueToSavedString(value: String): String = value

    override fun savedValueToData(value: String): String = value

    @Composable
    override fun Composable(modifier: Modifier) {
        val collectedValue by mValue.collectAsState()
        val collectedVisualTransformation by visualTransformation.collectAsState()
        val collectedError by error.collectAsState()
        uiFieldStyleData.ComposeTextField(
            value = valueToDisplayedString(collectedValue),
            onValueChange = {
                value = savedValueToData(it)
                dismissError()
            },
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
            visualTransformation = collectedVisualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            maxLine = maxLine,
            readOnly = readOnly,
            enabled = enabled,
            error = collectedError,
            interactionSource = null,
        )
    }
}
