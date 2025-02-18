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
 * TextUiField.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/22/2024 - for the Lunabee Compose library.
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
import kotlinx.coroutines.flow.StateFlow
import studio.lunabee.compose.foundation.uifield.UiField

abstract class TextUiField : UiField<String>() {
    abstract val visualTransformation: StateFlow<VisualTransformation>
    abstract val keyboardOptions: KeyboardOptions
    abstract val keyboardActions: KeyboardActions
    abstract val maxLine: Int

    @Composable
    override fun Composable(modifier: Modifier) {
        val collectedValue by mValue.collectAsState()
        val collectedVisualTransformation by visualTransformation.collectAsState()
        val collectedError by error.collectAsState()
        uiFieldStyleData.ComposeTextField(
            value = valueToDisplayedString(collectedValue),
            onValueChange = {
                value = it
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

    override fun valueToDisplayedString(value: String): String {
        return value
    }

    override fun valueToSavedString(value: String): String = value

    override fun savedValueToData(value: String): String = value
}
