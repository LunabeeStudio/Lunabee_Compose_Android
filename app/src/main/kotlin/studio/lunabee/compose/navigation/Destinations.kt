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
 * Destinations.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 2/5/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.navigation

object Destinations {
    const val MainRoute: String = "MainRoute"
    const val FoundationRoute: String = "FoundationRoute"
    const val HapticRoute: String = "HapticRoute"
    const val ThemeRoute: String = "ThemeRoute"
    const val CropRoute: String = "CropRoute"
    const val UiFieldRoute: String = "UiField"
    const val PresenterRoute: String = "Presenter"
    const val GlanceRoute: String = "GlanceRoute"
    const val ImageRoute: String = "ImageRoute"

    val BackNavigationScreen: List<String> =
        listOf(
            FoundationRoute,
            ThemeRoute,
            HapticRoute,
            CropRoute,
            UiFieldRoute,
            PresenterRoute,
            GlanceRoute,
            ImageRoute
        )
}
