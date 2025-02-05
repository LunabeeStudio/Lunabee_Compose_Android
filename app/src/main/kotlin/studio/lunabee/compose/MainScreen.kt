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
 * MainScreen.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 2/5/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose

import androidx.compose.runtime.Composable
import studio.lunabee.compose.common.MenuDescription
import studio.lunabee.compose.common.MenuSection
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.navigation.Directions

@Composable
fun MainScreen(
    directions: Directions,
) {
    MenuSection(
        menus = listOf(
            MenuDescription(
                titleRes = LbcTextSpec.StringResource(R.string.foundation_screen_title),
                subtitleRes = LbcTextSpec.StringResource(R.string.foundation_screen_subtitle),
                direction = directions.navigateToFoundation,
            ),
            MenuDescription(
                titleRes = LbcTextSpec.StringResource(R.string.haptic_screen_title),
                subtitleRes = LbcTextSpec.StringResource(R.string.haptic_screen_subtitle),
                direction = directions.navigateToHaptic,
            ),
            MenuDescription(
                titleRes = LbcTextSpec.StringResource(R.string.theme_screen_title),
                subtitleRes = LbcTextSpec.StringResource(R.string.theme_screen_subtitle),
                direction = directions.navigateToTheme,
            ),
            MenuDescription(
                titleRes = LbcTextSpec.StringResource(R.string.crop_screen_title),
                subtitleRes = LbcTextSpec.StringResource(R.string.crop_screen_subtitle),
                direction = directions.navigateToCrop,
            ),
            MenuDescription(
                titleRes = LbcTextSpec.StringResource(R.string.uiFields_screen_title),
                subtitleRes = LbcTextSpec.StringResource(R.string.uiFields_screen_subtitle),
                direction = directions.navigateToUiFields,
            ),
            MenuDescription(
                titleRes = LbcTextSpec.Raw("Presenter"),
                subtitleRes = LbcTextSpec.Raw("Composable architecture with presenter"),
                direction = directions.navigateToPresenterScreen,
            ),
            MenuDescription(
                titleRes = LbcTextSpec.StringResource(R.string.glance_title),
                subtitleRes = LbcTextSpec.StringResource(R.string.glance_description),
                direction = directions.navigateToGlanceScreen,
            ),
        ),
    )
}
