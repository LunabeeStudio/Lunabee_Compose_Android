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
 * MenuBackScreen.kt
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
import studio.lunabee.compose.R
import studio.lunabee.compose.extension.topAppBarElevation
import studio.lunabee.compose.lbctopappbar.material.LbcNavigationTopAppBar
import studio.lunabee.compose.material.common.section.MenuSection
import studio.lunabee.compose.model.MenuEntry
import studio.lunabee.compose.navigation.ToDirection

/**
 * Screen with a simple [MenuSection] that displays a list of clickable item, with a [LbcNavigationTopAppBar].
 *
 * @param title title to set in the [LbcNavigationTopAppBar]
 *
 * @param menus items element to me displayed in the list
 *
 * @param navigateToPreviousScreen action on [LbcNavigationTopAppBar] navigation pressed
 */
@Composable
fun MenuBackScreen(
    title: String,
    menus: List<MenuEntry>,
    navigateToPreviousScreen: ToDirection,
) {
    val lazyListState: LazyListState = rememberLazyListState()

    Scaffold(
        topBar = {
            LbcNavigationTopAppBar(
                title = title,
                navigationIconRes = R.drawable.ic_back,
                elevation = lazyListState.topAppBarElevation,
                onNavigationClicked = navigateToPreviousScreen,
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
