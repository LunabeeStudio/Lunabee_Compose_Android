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
 * HourPickerOption.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/23/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.field.time.option.hour

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.flow.MutableStateFlow
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.UiFieldOption
import studio.lunabee.compose.foundation.uifield.composable.TrailingAction

context(HourPickerHolder)
class HourPickerOption : UiFieldOption {
    private val isPickerVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val clickLabel: LbcTextSpec = hourPickerData.hourPickerClickLabel

    override fun onClick() {
        isPickerVisible.value = true
    }

    @Composable
    override fun Composable(modifier: Modifier) {
        val collectedIsPickedVisible by isPickerVisible.collectAsState()
        val focusManager = LocalFocusManager.current
        TrailingAction(
            image = hourPickerData.icon,
            onClick = { isPickerVisible.value = true },
            contentDescription = null,
            modifier = modifier,
        )
        if (collectedIsPickedVisible) {
            focusManager.clearFocus(true)
            UiFieldTimePicker(
                onDismiss = { isPickerVisible.value = false },
                hour = dateTime.hour,
                minutes = dateTime.minute,
                onValueChanged = ::onValueTimeChanged,
                confirmLabel = hourPickerData.hourPickerConfirmLabel,
                cancelLabel = hourPickerData.hourPickerCancelLabel,
            )
        }
    }
}