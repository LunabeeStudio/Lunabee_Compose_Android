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
import android.content.Intent
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.systemuicontroller.SystemUiController
import studio.lunabee.compose.MainScreen
import studio.lunabee.compose.accessibility.AccessibilityScreen
import studio.lunabee.compose.material.theme.LunabeeComposeMaterialTheme
import studio.lunabee.compose.material.topappbar.loading.LoadingTopAppBarScreen
import studio.lunabee.compose.material.topappbar.search.SearchTopAppBarScreen
import studio.lunabee.compose.material.topappbar.simple.SimpleTopAppBarScreen

@Composable
fun MainNavGraph(
    context: Context,
    navController: NavHostController,
    systemUiController: SystemUiController,
    directions: Directions,
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.MainRoute,
    ) {
        composable(
            route = Destinations.MainRoute,
        ) {
            LunabeeComposeMaterialTheme(
                systemUiController = systemUiController,
            ) {
                MainScreen(
                    navigateToSimpleTopAppBarScreen = directions.navigateToSimpleTopAppBarScreen,
                    navigateToLoadingTopAppBarScreen = directions.navigateToLoadingTopAppBarScreen,
                    navigateToSearchTopAppBarScreen = directions.navigateToSearchTopAppBarScreen,
                    navigateToAccessibilityScreen = directions.navigateToAccessibilityScreen,
                )
            }
        }

        composable(
            route = Destinations.SimpleTopAppBarRoute,
        ) {
            LunabeeComposeMaterialTheme(
                systemUiController = systemUiController,
            ) {
                SimpleTopAppBarScreen(
                    navigateToPreviousScreen = directions.navigateToPreviousScreen,
                )
            }
        }

        composable(
            route = Destinations.LoadingTopAppBarRoute,
        ) {
            LunabeeComposeMaterialTheme(
                systemUiController = systemUiController,
            ) {
                LoadingTopAppBarScreen(
                    navigateToPreviousScreen = directions.navigateToPreviousScreen,
                )
            }
        }

        composable(
            route = Destinations.SearchTopAppBarRoute,
        ) {
            LunabeeComposeMaterialTheme(
                systemUiController = systemUiController,
            ) {
                SearchTopAppBarScreen(
                    navigateToPreviousScreen = directions.navigateToPreviousScreen,
                )
            }
        }

        composable(
            route = Destinations.AccessibilityRoute,
        ) {
            LunabeeComposeMaterialTheme(
                systemUiController = systemUiController,
            ) {
                AccessibilityScreen(
                    navigateToPreviousScreen = directions.navigateToPreviousScreen,
                    openAccessibilitySettings = {
                        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                        context.startActivity(intent)
                    },
                )
            }
        }
    }
}
