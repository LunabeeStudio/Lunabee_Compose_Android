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
 * UiFieldDataImpl.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/23/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.field.data

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.field.UiFieldError

class UiFieldDataImpl : UiFieldData {
    @Composable
    override fun ComposeTextField(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier,
        placeholder: LbcTextSpec,
        label: LbcTextSpec,
        trailingIcon: @Composable RowScope.() -> Unit,
        visualTransformation: VisualTransformation,
        maxLine: Int,
        readOnly: Boolean,
        error: UiFieldError?,
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            placeholder = @Composable {
                Text(text = placeholder.string)
            },
            trailingIcon = {
                Row { trailingIcon() }
            },
            label = @Composable {
                Text(text = label.string)
            },
            maxLines = maxLine,
            readOnly = readOnly,
            visualTransformation = visualTransformation,
            isError = error != null,
            supportingText = if (error?.text != null) {
                @Composable {
                    Text(text = error.text.string)
                }
            } else {
                null
            },
        )
    }
}
