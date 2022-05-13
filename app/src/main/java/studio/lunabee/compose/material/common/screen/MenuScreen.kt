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
 * MenuScreen.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.material.common.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import studio.lunabee.compose.extension.topAppBarElevation
import studio.lunabee.compose.lbctopappbar.material.LbcTopAppBar
import studio.lunabee.compose.material.common.section.MenuSection
import studio.lunabee.compose.model.MenuEntry

/**
 * Simple screen with a list of clickable item and a [LbcTopAppBar]
 *
 * @param title text to set in the [LbcTopAppBar]
 *
 * @param menus item to display
 */
@Composable
fun MenuScreen(
    title: String,
    menus: List<MenuEntry>,
) {
    val lazyListState: LazyListState = rememberLazyListState()

    Scaffold(
        topBar = {
            LbcTopAppBar(
                title = title,
                elevation = lazyListState.topAppBarElevation,
                backgroundColor = MaterialTheme.colors.surface,
                applyStatusBarPadding = true,
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(paddingValues = it),
        ) {
            MenuSection(
                lazyListState = lazyListState,
                menus = menus,
            )
        }
    }
}
