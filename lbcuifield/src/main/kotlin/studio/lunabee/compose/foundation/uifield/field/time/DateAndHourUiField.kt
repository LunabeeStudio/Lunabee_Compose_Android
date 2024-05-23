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
 * HourUiField.kt
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
import studio.lunabee.compose.foundation.uifield.field.time.option.time.HourPickerHolder
import studio.lunabee.compose.foundation.uifield.field.time.option.time.HourPickerOption
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
class DateAndHourUiField(
    override val initialValue: LocalDateTime,
    override var label: LbcTextSpec,
    override var placeholder: LbcTextSpec,
    override val isFieldInError: (LocalDateTime) -> UiFieldError?,
    override val uiFieldData: UiFieldData = UiFieldDataImpl(),
    override val selectableDates: SelectableDates = DatePickerDefaults.AllDates,
    private val formatter: DateTimeFormatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.SHORT)
        .withZone(ZoneOffset.UTC)
) : TimeUiField<LocalDateTime>(), HourPickerHolder, DatePickerHolder {
    override val options: List<UiFieldOption> = listOf(
        DatePickerOption(),
        HourPickerOption(),
    )

    override fun valueToString(value: LocalDateTime): String {
        return formatter.format(value)
    }

    override val datePickerClickLabel: LbcTextSpec = LbcTextSpec.Raw("Picker Date")
    override val datePickerConfirmLabel: LbcTextSpec = LbcTextSpec.Raw("Confirm")
    override val datePickerCancelLabel: LbcTextSpec = LbcTextSpec.Raw("Cancel")

    override val hourPickerConfirmLabel: LbcTextSpec = LbcTextSpec.Raw("Confirm")
    override val hourPickerCancelLabel: LbcTextSpec = LbcTextSpec.Raw("Cancel")
    override val hourPickerClickLabel: LbcTextSpec = LbcTextSpec.Raw("Picker Hour")

    override val date: LocalDate
        get() = value.value.toLocalDate()

    override fun onValueDateChanged(date: LocalDate) {
        value.value = value.value
            .withDayOfMonth(date.dayOfMonth)
            .withMonth(date.monthValue)
            .withYear(date.year)
    }

    override val dateTime: LocalDateTime
        get() = value.value

    override fun onValueTimeChanged(hours: Int, minutes: Int) {
        value.value = value.value
            .withHour(hours)
            .withMinute(minutes)
    }
}
