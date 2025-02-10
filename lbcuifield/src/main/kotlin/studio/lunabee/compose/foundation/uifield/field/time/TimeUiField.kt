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
 * TimeUiField.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/23/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.field.time

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.text.input.VisualTransformation
import studio.lunabee.compose.foundation.uifield.UiField

abstract class TimeUiField<T> : UiField<T>() {

    @Composable
    override fun Composable(modifier: Modifier) {
        val collectedValue by mValue.collectAsState()
        val collectedError by error.collectAsState()
        // https://stackoverflow.com/a/70335041
        val interactionSource = if (options.isNotEmpty()) {
            remember { MutableInteractionSource() }.also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect { interaction ->
                        if (interaction is PressInteraction.Release) {
                            options.firstOrNull()?.onClick()
                        }
                    }
                }
            }
        } else {
            null
        }
        uiFieldStyleData.ComposeTextField(
            value = valueToDisplayedString(collectedValue),
            onValueChange = {},
            modifier = modifier
                .fillMaxWidth()
                .onFocusEvent {
                    if (it.hasFocus) {
                        dismissError()
                        hasBeenFocused = true
                    }
                },
            placeholder = placeholder,
            label = label,
            trailingIcon = if (options.isNotEmpty()) {
                { options.forEach { it.Composable(modifier = Modifier) } }
            } else {
                null
            },
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default,
            maxLine = 1,
            readOnly = true,
            error = collectedError,
            interactionSource = interactionSource,
        )
    }
}
