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
    const val MAIN_ROUTE: String = "MAIN_ROUTE"

    /**
     * Will navigate to [studio.lunabee.compose.material.MaterialScreen].
     */
    const val MATERIAL_ROUTE: String = "MATERIAL_ROUTE"

    /**
     * Will navigate to [studio.lunabee.compose.material.topappbar.TopAppBarScreen]
     */
    const val TOP_APP_BAR_ROUTE: String = "TOP_APP_BAR_ROUTE"
}
