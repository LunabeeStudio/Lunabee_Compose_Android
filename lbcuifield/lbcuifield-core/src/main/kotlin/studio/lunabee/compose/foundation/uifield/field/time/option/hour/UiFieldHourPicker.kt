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
 * UiFieldHourPicker.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/23/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.field.time.option.hour

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import studio.lunabee.compose.core.LbcTextSpec

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiFieldTimePicker(
    hour: Int,
    minutes: Int,
    onDismiss: () -> Unit,
    onValueChanged: (Int, Int) -> Unit,
    confirmLabel: LbcTextSpec,
    cancelLabel: LbcTextSpec
) {
    val state =
        rememberTimePickerState(
            initialHour = hour,
            initialMinute = minutes
        )
    TimePickerDialog(
        onDismiss = onDismiss,
        content = {
            TimePicker(state = state)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onValueChanged(state.hour, state.minute)
                    onDismiss()
                }
            ) {
                Text(text = confirmLabel.string)
            }
        },
        cancelButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = cancelLabel.string)
            }
        }
    )
}

/**
 * TODO Replace later when its added to lib
 * Taken from sample
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/material3/material3/samples/src/main/java/androidx/compose/material3/samples/TimePickerSamples.kt
 */
@Composable
fun TimePickerDialog(
    confirmButton: @Composable () -> Unit,
    cancelButton: @Composable () -> Unit,
    onDismiss: () -> Unit,
    toggle: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier =
            Modifier
                .width(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(20.dp))
                content()
                Row(
                    modifier =
                    Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    cancelButton()
                    confirmButton()
                }
            }
        }
    }
}
