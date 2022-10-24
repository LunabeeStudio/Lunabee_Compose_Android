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
 * MainScreen.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import studio.lunabee.compose.material.common.screen.MenuScreen
import studio.lunabee.compose.model.MenuEntry
import studio.lunabee.compose.navigation.ToDirection

@Composable
fun MainScreen(
    navigateToSimpleTopAppBarScreen: ToDirection,
    navigateToLoadingTopAppBarScreen: ToDirection,
    navigateToSearchTopAppBarScreen: ToDirection,
    navigateToAccessibilityScreen: ToDirection,
    navigateToTextScreen: ToDirection,
    navigateToVerticalBarGraphScreen: ToDirection,
    navigateToMaterial3: ToDirection,
) {
    val menus: List<MenuEntry> = remember {
        listOf(
            MenuEntry(
                titleRes = R.string.top_bar_list_title,
                subtitleRes = R.string.top_bar_list_subtitle,
                direction = navigateToSimpleTopAppBarScreen,
            ),
            MenuEntry(
                titleRes = R.string.top_bar_loading_list_title,
                subtitleRes = R.string.top_bar_loading_list_subtitle,
                direction = navigateToLoadingTopAppBarScreen,
            ),
            MenuEntry(
                titleRes = R.string.top_bar_search_list_title,
                subtitleRes = R.string.top_bar_search_list_subtitle,
                direction = navigateToSearchTopAppBarScreen,
            ),
            MenuEntry(
                titleRes = R.string.accessibility_title,
                subtitleRes = R.string.accessibility_subtitle,
                direction = navigateToAccessibilityScreen,
            ),
            MenuEntry(
                titleRes = R.string.text_screen_title,
                subtitleRes = R.string.text_screen_subtitle,
                direction = navigateToTextScreen,
            ),
            MenuEntry(
                titleRes = R.string.vertical_bar_graph_screen_title,
                subtitleRes = R.string.vertical_bar_graph_screen_subtitle,
                direction = navigateToVerticalBarGraphScreen,
            ),
            MenuEntry(
                titleRes = R.string.material3_screen_title,
                subtitleRes = R.string.material3_screen_subtitle,
                direction = navigateToMaterial3,
            ),
        )
    }

    MenuScreen(
        title = stringResource(id = R.string.select_your_theme_title),
        menus = menus,
    )
}
