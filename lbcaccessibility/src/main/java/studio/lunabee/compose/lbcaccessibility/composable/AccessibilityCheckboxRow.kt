/*
 * Copyright © 2022 Lunabee Studio
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
 * AccessibilityCheckboxRow.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/11/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbcaccessibility.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import studio.lunabee.compose.lbcaccessibility.extension.addSemanticsForCheckBox
import studio.lunabee.compose.lbcaccessibility.model.AccessibilityDescription

/**
 * [androidx.compose.material.Checkbox] component with accessibility:
 * - Checkbox is not clickable
 * - Row handle checked state with [androidx.compose.foundation.selection.toggleable]
 *
 * This Composable is stateful, checked value is already handled.
 *
 * @param initialCheckStateValue initial value for [androidx.compose.material.Checkbox]
 *
 * @param accessibilityDescription see [AccessibilityDescription]
 *
 * @param modifier default [Modifier] that could be override and will be use for the [Row]
 *
 * @param horizontalArrangement default [Arrangement.Horizontal] for the [Row]
 *
 * @param verticalAlignment default [Alignment.Vertical] for the [Row]
 *
 * @param rowContent custom content to display with the [Checkbox] in a [RowScope]
 */
@Composable
fun AccessibilityCheckBoxRow(
    initialCheckStateValue: Boolean,
    accessibilityDescription: AccessibilityDescription,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    rowContent: @Composable RowScope.() -> Unit,
) {
    var isChecked: Boolean by rememberSaveable { mutableStateOf(value = initialCheckStateValue) }

    AccessibilityCheckBoxRowStateless(
        currentCheckedStateValue = isChecked,
        onCheckedChange = { isChecked = it },
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        accessibilityDescription = accessibilityDescription.copy(
            stateDescription = accessibilityDescription.stateDescription?.copy(currentStateValue = isChecked),
        ),
        rowContent = rowContent,
    )
}

/**
 * [androidx.compose.material.Checkbox] component with accessibility:
 * - Checkbox is not clickable
 * - Row handle checked state with [androidx.compose.foundation.selection.toggleable]
 *
 * This Composable is stateless, checked value has to be handled.
 *
 * @param currentCheckedStateValue current value for [androidx.compose.material.Checkbox]
 *
 * @param onCheckedChange on user interaction
 *
 * @param accessibilityDescription see [AccessibilityDescription]
 *
 * @param modifier default [Modifier] that could be override and will be use for the [Row]
 *
 * @param horizontalArrangement default [Arrangement.Horizontal] for the [Row]
 *
 * @param verticalAlignment default [Alignment.Vertical] for the [Row]
 *
 * @param rowContent custom content to display with the [Checkbox] in a [RowScope]
 */
@Composable
fun AccessibilityCheckBoxRowStateless(
    currentCheckedStateValue: Boolean,
    onCheckedChange: (newCheckedStateValue: Boolean) -> Unit,
    accessibilityDescription: AccessibilityDescription,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    rowContent: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = Modifier
            .addSemanticsForCheckBox(
                currentCheckedStateValue = currentCheckedStateValue,
                onCheckedChange = onCheckedChange,
                accessibilityDescription = accessibilityDescription,
            )
            .then(modifier), // order is important
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
    ) {
        rowContent()

        Checkbox(
            checked = currentCheckedStateValue,
            onCheckedChange = null,
            // keep aspect for Checkbox (padding is removed when onCheckedChange is null)
            modifier = Modifier
                .defaultMinSize(minHeight = 48.dp, minWidth = 48.dp),
        )
    }
}
