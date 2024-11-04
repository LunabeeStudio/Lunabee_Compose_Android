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
 * DatePickerOption.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/23/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.field.time.option.date

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.UiFieldOption
import studio.lunabee.compose.foundation.uifield.composable.TrailingAction

context(DatePickerHolder)
class DatePickerOption : UiFieldOption {
    private val isPickerVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val clickLabel: LbcTextSpec = datePickerData.datePickerClickLabel

    override fun onClick() {
        isPickerVisible.value = true
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Composable(modifier: Modifier) {
        val collectedIsPickedVisible by isPickerVisible.collectAsState()
        TrailingAction(
            image = datePickerData.icon,
            onClick = { isPickerVisible.value = true },
            contentDescription = null,
            modifier = modifier,
        )
        if (collectedIsPickedVisible) {
            UiFieldDatePicker(
                date = date,
                onDismiss = { isPickerVisible.value = false },
                onValueChanged = ::onValueDateChanged,
                cancelLabel = datePickerData.datePickerCancelLabel,
                confirmLabel = datePickerData.datePickerConfirmLabel,
                selectableDates = selectableDates,
            )
        }
    }
}
