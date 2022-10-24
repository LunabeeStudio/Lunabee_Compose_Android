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
 * Destinations.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.navigation

/**
 * All navigation route.
 */
object Destinations {
    /**
     * Will navigate to [studio.lunabee.compose.MainScreen].
     */
    const val MainRoute: String = "MainRoute"

    /**
     * Will navigate to [studio.lunabee.compose.accessibility.AccessibilityScreen]
     */
    const val AccessibilityRoute: String = "AccessibilityRoute"

    /**
     * Will navigate to [studio.lunabee.compose.material.topappbar.simple.SimpleTopAppBarScreen]
     */
    const val SimpleTopAppBarRoute: String = "SimpleTopAppBarRoute"

    /**
     * Will navigate to [studio.lunabee.compose.material.topappbar.loading.LoadingTopAppBarScreen]
     */
    const val LoadingTopAppBarRoute: String = "LoadingTopAppBarRoute"

    /**
     * Will navigate to [studio.lunabee.compose.material.topappbar.search.SearchTopAppBarScreen]
     */
    const val SearchTopAppBarRoute: String = "SearchTopAppBarRoute"

    /**
     * Will navigate to [studio.lunabee.compose.material.text.TextScreen]
     */
    const val TextRoute: String = "TextRoute"

    /**
     * Will navigate to [studio.lunabee.compose.graph.VerticalBarGraphScreen]
     */
    const val VerticalBarGraphRoute: String = "VerticalBarGraphRoute"

    /**
     * Will navigate to [studio.lunabee.compose.material3.theme.ThemeScreen]
     */
    const val ThemeRoute: String = "ThemeRoute"

    /**
     * Will navigate to [studio.lunabee.compose.material3.Material3Screen]
     */
    const val Material3Route: String = "Material3"
}
