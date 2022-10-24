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

package studio.lunabee.compose.material3

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.android.material.color.DynamicColors

/**
 * Material theme.
 */
@Composable
fun LunabeeComposeMaterial3Theme(
    isSystemInDarkTheme: Boolean = isSystemInDarkTheme(),
    systemUiController: SystemUiController? = null,
    customColorScheme: ColorScheme? = null,
    content: @Composable () -> Unit,
) {
    val isMaterialYouEnabled = !LocalInspectionMode.current && DynamicColors.isDynamicColorAvailable()

    @Suppress("NewApi") // Already check by isDynamicColorAvailable
    val colorScheme = when {
        customColorScheme != null -> customColorScheme
        isMaterialYouEnabled && isSystemInDarkTheme -> dynamicDarkColorScheme(context = LocalContext.current)
        isMaterialYouEnabled -> dynamicLightColorScheme(context = LocalContext.current)
        isSystemInDarkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }

    SideEffect {
        systemUiController?.setStatusBarColor(Color.Transparent, darkIcons = !isSystemInDarkTheme)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
