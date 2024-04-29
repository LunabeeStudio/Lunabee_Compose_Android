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

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import studio.lunabee.compose.MainScreen
import studio.lunabee.compose.demo.crop.CropScreen
import studio.lunabee.compose.demo.foundation.TextScreen
import studio.lunabee.compose.demo.haptic.HapticScreen
import studio.lunabee.compose.demo.theme.ThemeScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    directions: Directions,
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.MainRoute,
    ) {
        composable(
            route = Destinations.MainRoute,
        ) {
            MainScreen(directions = directions)
        }

        composable(
            route = Destinations.FoundationRoute,
        ) {
            TextScreen()
        }

        composable(
            route = Destinations.HapticRoute,
        ) {
            HapticScreen()
        }

        composable(
            route = Destinations.ThemeRoute,
        ) {
            ThemeScreen()
        }

        composable(
            route = Destinations.CropRoute,
        ) {
            CropScreen()
        }
    }
}
