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
 * UifieldsScreen.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/22/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.uifield

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.field.UiFieldError
import studio.lunabee.compose.foundation.uifield.field.text.NormalUiTextField
import studio.lunabee.compose.foundation.uifield.field.text.PasswordUiTextField
import studio.lunabee.compose.foundation.uifield.field.time.DateAndHourUiField
import studio.lunabee.compose.foundation.uifield.field.time.DateUiField
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiFieldsScreen() {
    val normalUiTextField = remember {
        NormalUiTextField(
            initialValue = "",
            placeholder = LbcTextSpec.Raw("This is a normal text field"),
            label = LbcTextSpec.Raw("Normal"),
            isFieldInError = { value ->
                if (value.isBlank()) UiFieldError(LbcTextSpec.Raw("Should not be null")) else null
            },
        )
    }
    val passwordUiTextField = remember {
        PasswordUiTextField(
            initialValue = "",
            placeholder = LbcTextSpec.Raw("This is a password Text Field"),
            label = LbcTextSpec.Raw("Password"),
            isFieldInError = {
                null
            },
        )
    }
    val dateAndHourUiField = remember {
        DateAndHourUiField(
            initialValue = LocalDateTime.now(),
            placeholder = LbcTextSpec.Raw("This is a Date and Hour UiField"),
            label = LbcTextSpec.Raw("Date and Hour"),
            isFieldInError = {
                null
            },
        )
    }
    val dateUiField = remember {
        DateUiField(
            initialValue = LocalDate.now(),
            placeholder = LbcTextSpec.Raw("This is a Date UiField"),
            label = LbcTextSpec.Raw("Date in future"),
            isFieldInError = {
                null
            },
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return Instant.now().toEpochMilli() < utcTimeMillis
                }
            },
        )
    }
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        normalUiTextField.Composable(modifier = Modifier.fillMaxWidth())
        passwordUiTextField.Composable(modifier = Modifier.fillMaxWidth())
        dateAndHourUiField.Composable(modifier = Modifier.fillMaxWidth())
        dateUiField.Composable(modifier = Modifier.fillMaxWidth())

        Button(
            onClick = {
                normalUiTextField.checkAndDisplayError()
                passwordUiTextField.checkAndDisplayError()
                dateAndHourUiField.checkAndDisplayError()
                dateUiField.checkAndDisplayError()
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Check error")
        }
    }
}
