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
 * LunabeeComposeTheme.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.material.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.SystemUiController

/**
 * Material theme.
 */
@Composable
fun LunabeeComposeMaterialTheme(
    isSystemInDarkTheme: Boolean = isSystemInDarkTheme(),
    systemUiController: SystemUiController? = null,
    content: @Composable () -> Unit,
) {
    val colors = if (isSystemInDarkTheme) {
        darkColors(
            primary = Color.White,
            primaryVariant = Color.White,
            secondary = Color.LightGray,
            secondaryVariant = Color.LightGray,
        )
    } else {
        lightColors(
            primary = Color.LightGray,
            primaryVariant = Color.LightGray,
            secondary = Color.Black,
            secondaryVariant = Color.Black,
        )
    }

    SideEffect {
        systemUiController?.setStatusBarColor(Color.Transparent, darkIcons = !isSystemInDarkTheme)
    }

    MaterialTheme(
        colors = colors,
        content = content,
    )
}
