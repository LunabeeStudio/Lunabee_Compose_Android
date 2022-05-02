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
 * MenuSection.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.material.common.section

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import studio.lunabee.compose.material.common.item.MenuItem
import studio.lunabee.compose.model.MenuEntry

/**
 * Build a [androidx.compose.foundation.lazy.LazyColumn] with provided items.
 *
 * @param lazyListState a remembered [androidx.compose.foundation.lazy.LazyListState]
 *
 * @param menus items to display
 */
@Composable
fun MenuSection(
    lazyListState: LazyListState,
    menus: List<MenuEntry>,
) {
    LazyColumn(
        state = lazyListState,
    ) {
        items(
            items = menus,
            key = { it.titleRes },
        ) { menu ->
            MenuItem(
                menu = menu,
            )
            Divider()
        }
    }
}
