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
 * Material3Screen.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/21/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.material3

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import studio.lunabee.compose.R
import studio.lunabee.compose.material.common.screen.MenuBack3Screen
import studio.lunabee.compose.model.MenuEntry
import studio.lunabee.compose.navigation.ToDirection

@Composable
fun Material3Screen(
    navigateToTheme: ToDirection,
    navigateBack: ToDirection,
) {
    MenuBack3Screen(
        title = stringResource(id = R.string.material3_screen_title),
        menus = listOf(
            MenuEntry(
                titleRes = R.string.material3_theme_title,
                subtitleRes = R.string.material3_theme_subtitle,
                direction = navigateToTheme,
            ),
        ),
        navigateToPreviousScreen = navigateBack,
    )
}
