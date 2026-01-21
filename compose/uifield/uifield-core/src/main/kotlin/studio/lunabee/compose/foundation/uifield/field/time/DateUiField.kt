/*
 * Copyright (c) 2026 Lunabee Studio
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
class DateUiField(
    override val initialValue: LocalDate?,
    override var label: LbcTextSpec?,
    override var placeholder: LbcTextSpec?,
    override val id: String,
    override val savedStateHandle: SavedStateHandle,
    override val datePickerData: DatePickerData,
    override val isFieldInError: (LocalDate?) -> UiFieldError?,
    override val uiFieldStyleData: UiFieldStyleData = UiFieldStyleDataImpl(),
    override val selectableDates: SelectableDates = DatePickerDefaults.AllDates,
    private val formatter: DateTimeFormatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.SHORT)
        .withLocale(Locale.getDefault()),
    override val onValueChange: (LocalDate?) -> Unit = {},
    override val readOnly: Boolean = false,
    override val enabled: Boolean = true,
) : TimeUiField<LocalDate?>(), DatePickerHolder {
    override val options: List<UiFieldOption> = listOf(DatePickerOption(enabled && !readOnly, this))

    override fun savedValueToData(value: String): LocalDate = LocalDate.parse(value)

    override fun valueToSavedString(value: LocalDate?): String = value?.let(LocalDate::toString).orEmpty()

    override fun valueToDisplayedString(value: LocalDate?): String = value?.let(formatter::format).orEmpty()

    override val date: LocalDate?
        get() = value

    override fun onValueDateChanged(date: LocalDate) {
        value = date
        checkAndDisplayError()
    }
}
