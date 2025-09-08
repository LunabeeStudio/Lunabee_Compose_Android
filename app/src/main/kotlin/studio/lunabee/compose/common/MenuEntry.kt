/*
 * Copyright (c) 2025 Lunabee Studio
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
 * MenuEntry.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 2/5/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuEntry(menu: MenuDescription) {
    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .clickable { menu.direction() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .defaultMinSize(minHeight = 56.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = menu.titleRes.string,
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        menu.subtitleRes?.let { subtitle ->
            Text(
                text = subtitle.string,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
