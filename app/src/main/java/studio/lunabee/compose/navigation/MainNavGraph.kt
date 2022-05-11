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
 * MainNavGraph.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.systemuicontroller.SystemUiController
import studio.lunabee.compose.MainScreen
import studio.lunabee.compose.accessibility.AccessibilityScreen
import studio.lunabee.compose.lbcaccessibility.extension.openAccessibilitySettings
import studio.lunabee.compose.material.MaterialScreen
import studio.lunabee.compose.material.theme.LunabeeComposeMaterialTheme

@Composable
fun MainNavGraph(
    context: Context,
    navController: NavHostController,
    systemUiController: SystemUiController,
    directions: Directions,
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.MAIN_ROUTE,
    ) {
        composable(
            route = Destinations.MAIN_ROUTE,
        ) {
            LunabeeComposeMaterialTheme(
                systemUiController = systemUiController,
            ) {
                MainScreen(
                    navigateToMaterialScreen = directions.navigateToMaterialScreen,
                    navigateToMaterial3Screen = directions.navigateToMaterial3Screen,
                    navigateToAccessibilityScreen = directions.navigateToAccessibilityScreen,
                )
            }
        }

        composable(
            route = Destinations.MATERIAL_ROUTE,
        ) {
            LunabeeComposeMaterialTheme(
                systemUiController = systemUiController,
            ) {
                MaterialScreen(
                    navigateToPreviousScreen = directions.navigateToPreviousScreen,
                    navigateToTopAppBarScreen = directions.navigateToTopAppBarScreen,
                )
            }
        }

        composable(
            route = Destinations.ACCESSIBILITY_ROUTE,
        ) {
            LunabeeComposeMaterialTheme(
                systemUiController = systemUiController,
            ) {
                AccessibilityScreen(
                    navigateToPreviousScreen = directions.navigateToPreviousScreen,
                    openAccessibilitySettings = { context.openAccessibilitySettings() },
                )
            }
        }
    }
}
