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
 * LbcThemeUtilities.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/21/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import material.Scheme
import material.TonalPalette

object LbcThemeUtilities {
    /**
     * Generate a [ColorScheme] ready to use with a [androidx.compose.material3.MaterialTheme].
     * @param color base primary color. All colors will be generated from this one.
     * @param isInDarkMode
     */
    @JvmStatic
    fun getMaterialColorSchemeFromColor(
        color: Color,
        isInDarkMode: Boolean,
    ): ColorScheme {
        return schemeToColorScheme(
            scheme = if (isInDarkMode) {
                Scheme.dark(color.toArgb())
            } else {
                Scheme.light(color.toArgb())
            },
        )
    }

    /**
     * Get color variant from a base color and the desired tone.
     * @param color base color
     * @param tone value between 0 and 100 representing your tone.
     */
    @JvmStatic
    fun getToneForColor(color: Color, tone: Int): Color {
        assert(value = tone in 0..100, lazyMessage = { "Tone value must be in range 0..100" })
        return Color(color = TonalPalette.fromInt(color.toArgb()).tone(tone))
    }

    private fun schemeToColorScheme(scheme: Scheme): ColorScheme {
        return ColorScheme(
            primary = Color(color = scheme.primary),
            onPrimary = Color(color = scheme.onPrimary),
            primaryContainer = Color(color = scheme.primaryContainer),
            onPrimaryContainer = Color(color = scheme.onPrimaryContainer),
            inversePrimary = Color(color = scheme.inversePrimary),
            secondary = Color(color = scheme.secondary),
            onSecondary = Color(color = scheme.onSecondary),
            secondaryContainer = Color(color = scheme.secondaryContainer),
            onSecondaryContainer = Color(color = scheme.onSecondaryContainer),
            tertiary = Color(color = scheme.tertiary),
            onTertiary = Color(color = scheme.onTertiary),
            tertiaryContainer = Color(color = scheme.tertiaryContainer),
            onTertiaryContainer = Color(color = scheme.onTertiaryContainer),
            background = Color(color = scheme.background),
            onBackground = Color(color = scheme.onBackground),
            surface = Color(color = scheme.surface),
            onSurface = Color(color = scheme.onSurface),
            surfaceTint = Color(color = scheme.primary),
            surfaceVariant = Color(color = scheme.surfaceVariant),
            onSurfaceVariant = Color(color = scheme.onSurfaceVariant),
            inverseSurface = Color(color = scheme.inverseSurface),
            inverseOnSurface = Color(color = scheme.inverseOnSurface),
            error = Color(color = scheme.error),
            onError = Color(color = scheme.onError),
            errorContainer = Color(color = scheme.errorContainer),
            onErrorContainer = Color(color = scheme.onErrorContainer),
            outline = Color(color = scheme.outline),
            outlineVariant = Color(color = scheme.outlineVariant),
            scrim = Color(color = scheme.scrim),
        )
    }
}
