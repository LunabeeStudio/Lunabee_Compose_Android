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
 * DateUiField.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/23/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.field.time

import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.UiFieldOption
import studio.lunabee.compose.foundation.uifield.field.UiFieldError
import studio.lunabee.compose.foundation.uifield.field.data.UiFieldData
import studio.lunabee.compose.foundation.uifield.field.data.UiFieldDataImpl
import studio.lunabee.compose.foundation.uifield.field.time.option.date.DatePickerHolder
import studio.lunabee.compose.foundation.uifield.field.time.option.date.DatePickerOption
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
class DateUiField(
    override val initialValue: LocalDate,
    override var label: LbcTextSpec,
    override var placeholder: LbcTextSpec,
    override val isFieldInError: (LocalDate) -> UiFieldError?,
    override val uiFieldData: UiFieldData = UiFieldDataImpl(),
    override val selectableDates: SelectableDates = DatePickerDefaults.AllDates,
    private val formatter: DateTimeFormatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.SHORT)
        .withZone(ZoneOffset.UTC)
) : TimeUiField<LocalDate>(), DatePickerHolder {
    override val options: List<UiFieldOption> = listOf(DatePickerOption())

    override fun valueToString(value: LocalDate): String {
        return formatter.format(value)
    }

    override val datePickerConfirmLabel: LbcTextSpec = LbcTextSpec.Raw("Confirm")
    override val datePickerCancelLabel: LbcTextSpec = LbcTextSpec.Raw("Cancel")
    override val datePickerClickLabel: LbcTextSpec = LbcTextSpec.Raw("Picker Date")

    override val date: LocalDate
        get() = value.value

    override fun onValueDateChanged(date: LocalDate) {
        value.value = date
    }
}
