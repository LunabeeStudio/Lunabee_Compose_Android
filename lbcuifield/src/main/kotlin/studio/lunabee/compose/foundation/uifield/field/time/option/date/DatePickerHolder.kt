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
 * DatePickerHolder.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/23/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.field.time.option.date

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import studio.lunabee.compose.core.LbcTextSpec
import java.time.LocalDate

interface DatePickerHolder {
    val datePickerClickLabel: LbcTextSpec
    val datePickerConfirmLabel: LbcTextSpec
    val datePickerCancelLabel: LbcTextSpec
    val date: LocalDate

    @OptIn(ExperimentalMaterial3Api::class)
    val selectableDates: SelectableDates

    fun onValueDateChanged(date: LocalDate)
}
