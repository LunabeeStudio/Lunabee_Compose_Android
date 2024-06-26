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

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.UiFieldOption
import studio.lunabee.compose.foundation.uifield.field.UiFieldError
import studio.lunabee.compose.foundation.uifield.field.style.UiFieldStyleData
import studio.lunabee.compose.foundation.uifield.field.style.UiFieldStyleDataImpl
import studio.lunabee.compose.foundation.uifield.field.text.option.VisibilityFieldOption
import studio.lunabee.compose.foundation.uifield.field.text.option.VisibilityOptionData
import studio.lunabee.compose.foundation.uifield.field.text.option.VisibilityOptionHolder

class PasswordUiTextField(
    override var label: LbcTextSpec,
    override val initialValue: String,
    override var placeholder: LbcTextSpec,
    override val isFieldInError: (String) -> UiFieldError?,
    override val visibilityOptionData: VisibilityOptionData,
    override val id: String,
    override val savedStateHandle: SavedStateHandle,
    override val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    override val keyboardActions: KeyboardActions = KeyboardActions.Default,
    override val uiFieldStyleData: UiFieldStyleData = UiFieldStyleDataImpl(),
    override val onValueChange: (String) -> Unit = {},
) : TextUiField(), VisibilityOptionHolder {
    private val mVisualTransformation: MutableStateFlow<VisualTransformation> =
        MutableStateFlow(PasswordVisualTransformation())
    override val visualTransformation: StateFlow<VisualTransformation> =
        mVisualTransformation.asStateFlow()

    override fun onVisibilityToggle() {
        if (visualTransformation.value == VisualTransformation.None) {
            mVisualTransformation.value = PasswordVisualTransformation()
        } else {
            mVisualTransformation.value = VisualTransformation.None
        }
    }

    override val maxLine: Int = 1
    override val options: List<UiFieldOption> = listOf(
        VisibilityFieldOption(),
    )
}
