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
        val collectedValue by value.collectAsState()
        val collectedVisualTransformation by visualTransformation.collectAsState()
        val collectedError by error.collectAsState()
        uiFieldData.ComposeTextField(
            value = valueToString(collectedValue),
            onValueChange = { value.value = it },
            modifier = modifier,
            placeholder = placeholder,
            trailingIcon = { options.forEach { it.Composable(modifier = Modifier) } },
            visualTransformation = collectedVisualTransformation,
            maxLine = maxLine,
            readOnly = false,
            label = label,
            error = collectedError,
        )
    }

    override fun valueToString(value: String): String {
        return value
    }
}
