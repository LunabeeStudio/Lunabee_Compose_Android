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
 * MenuItem.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.material.common.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import studio.lunabee.compose.model.MenuEntry

/**
 * Item build on [MenuEntry] that will be displayed in [studio.lunabee.compose.material.common.section.MenuSection].
 *
 * @param menu a [MenuEntry] object
 */
@Composable
fun MenuItem(
    menu: MenuEntry,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { menu.direction() }
            .padding(horizontal = 16.dp)
            .height(height = 56.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = menu.titleRes),
        )
        Text(
            text = stringResource(id = menu.subtitleRes),
            style = MaterialTheme.typography.caption,
        )
    }
}
