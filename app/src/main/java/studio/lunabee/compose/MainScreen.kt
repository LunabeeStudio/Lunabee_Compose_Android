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
import studio.lunabee.compose.common.MenuDescription
import studio.lunabee.compose.common.MenuSection
import studio.lunabee.compose.navigation.Directions

@Composable
fun MainScreen(
    directions: Directions,
) {
    MenuSection(
        menus = listOf(
            MenuDescription(
                titleRes = R.string.accessibility_title,
                subtitleRes = R.string.accessibility_subtitle,
                direction = directions.navigateToAccessibility,
            ),
            MenuDescription(
                titleRes = R.string.foundation_screen_title,
                subtitleRes = R.string.foundation_screen_subtitle,
                direction = directions.navigateToFoundation,
            ),
            MenuDescription(
                titleRes = R.string.theme_screen_title,
                subtitleRes = R.string.theme_screen_subtitle,
                direction = directions.navigateToTheme,
            ),
        ),
    )
}
