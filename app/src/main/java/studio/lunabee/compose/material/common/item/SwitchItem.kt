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
 * SwitchItem.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.material.common.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Display a Switch item with. This composable is not in charge to handle check state.
 *
 * @param modifier custom [Modifier] if needed
 *
 * @param switchText text to displayed with the switch
 *
 * @param isCheckedByDefault set a default value to switch check state
 *
 * @param onCheckChange called when switch state changes
 */
@Composable
fun SwitchItem(
    modifier: Modifier = Modifier,
    switchText: String,
    isCheckedByDefault: Boolean,
    onCheckChange: (isChecked: Boolean) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = switchText,
        )
        Switch(
            checked = isCheckedByDefault,
            onCheckedChange = onCheckChange,
        )
    }
}
