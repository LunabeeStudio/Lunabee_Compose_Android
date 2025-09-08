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
import androidx.lifecycle.SavedStateHandle
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.UiFieldOption
import studio.lunabee.compose.foundation.uifield.field.UiFieldError
import studio.lunabee.compose.foundation.uifield.field.style.UiFieldStyleData
import studio.lunabee.compose.foundation.uifield.field.style.UiFieldStyleDataImpl
import studio.lunabee.compose.foundation.uifield.field.time.option.date.DatePickerData
import studio.lunabee.compose.foundation.uifield.field.time.option.date.DatePickerHolder
import studio.lunabee.compose.foundation.uifield.field.time.option.date.DatePickerOption
import studio.lunabee.compose.foundation.uifield.field.time.option.hour.HourPickerData
import studio.lunabee.compose.foundation.uifield.field.time.option.hour.HourPickerHolder
import studio.lunabee.compose.foundation.uifield.field.time.option.hour.HourPickerOption
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
class DateAndHourUiField(
    override val initialValue: LocalDateTime?,
    override var label: LbcTextSpec?,
    override var placeholder: LbcTextSpec?,
    override val isFieldInError: (LocalDateTime?) -> UiFieldError?,
    override val id: String,
    override val savedStateHandle: SavedStateHandle,
    override val uiFieldStyleData: UiFieldStyleData = UiFieldStyleDataImpl(),
    override val selectableDates: SelectableDates = DatePickerDefaults.AllDates,
    override val datePickerData: DatePickerData,
    override val hourPickerData: HourPickerData,
    private val formatter: DateTimeFormatter =
        DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.SHORT)
            .withZone(ZoneOffset.UTC),
    override val onValueChange: (LocalDateTime?) -> Unit = {},
    override val readOnly: Boolean = false,
    override val enabled: Boolean = true
) : TimeUiField<LocalDateTime?>(),
    HourPickerHolder,
    DatePickerHolder {
    override val options: List<UiFieldOption> =
        listOf(
            DatePickerOption(enabled && !readOnly, this),
            HourPickerOption(enabled && !readOnly, this)
        )

    override fun savedValueToData(value: String): LocalDateTime = LocalDateTime.parse(value)

    override fun valueToSavedString(value: LocalDateTime?): String = value.toString()

    override fun valueToDisplayedString(value: LocalDateTime?): String =
        value?.let(formatter::format).orEmpty()

    override val date: LocalDate?
        get() = value?.toLocalDate()

    override fun onValueDateChanged(date: LocalDate) {
        value =
            (value ?: LocalDateTime.now().withHour(0).withMinute(0))
                .withDayOfMonth(date.dayOfMonth)
                .withMonth(date.monthValue)
                .withYear(date.year)
        checkAndDisplayError()
    }

    override val dateTime: LocalDateTime?
        get() = value

    override fun onValueTimeChanged(hours: Int, minutes: Int) {
        value =
            (value ?: LocalDateTime.now())
                .withHour(hours)
                .withMinute(minutes)
        checkAndDisplayError()
    }
}
