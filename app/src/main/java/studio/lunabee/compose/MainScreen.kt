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

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import studio.lunabee.compose.material.common.screen.MenuScreen
import studio.lunabee.compose.material.theme.LunabeeComposeMaterialTheme
import studio.lunabee.compose.model.MenuEntry
import studio.lunabee.compose.navigation.ToDirection

@Composable
fun MainScreen(
    navigateToMaterialScreen: ToDirection,
    navigateToMaterial3Screen: ToDirection,
) {
    val menus: List<MenuEntry> = remember {
        listOf(
            MenuEntry(
                titleRes = R.string.material_title,
                subtitleRes = R.string.material_subtitle,
                direction = navigateToMaterialScreen,
            ),
            MenuEntry(
                titleRes = R.string.material3_title,
                subtitleRes = R.string.material3_subtitle,
                direction = navigateToMaterial3Screen,
            )
        )
    }

    MenuScreen(
        title = stringResource(id = R.string.select_your_theme_title),
        menus = menus,
    )
}

@Preview
@Composable
private fun MainScreenDayPreview() {
    LunabeeComposeMaterialTheme {
        MainScreen(
            navigateToMaterialScreen = { },
            navigateToMaterial3Screen = { },
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenDarkPreview() {
    MainScreenDayPreview()
}
