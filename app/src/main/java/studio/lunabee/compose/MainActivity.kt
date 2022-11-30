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
 * MainActivity.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import studio.lunabee.compose.common.AppDemoTheme
import studio.lunabee.compose.navigation.Destinations
import studio.lunabee.compose.navigation.Directions
import studio.lunabee.compose.navigation.MainNavGraph

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // For status bar color with elevation.
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()
            val systemUiController = rememberSystemUiController()
            val directions = remember { Directions(navController = navController) }
            var title: String by rememberSaveable {
                mutableStateOf(value = getString(R.string.application_name))
            }
            var shouldShowBackNav: Boolean by rememberSaveable {
                mutableStateOf(value = false)
            }

            LaunchedEffect(navController) {
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    title = when (destination.route) {
                        Destinations.MainRoute -> getString(R.string.application_name)
                        Destinations.ThemeRoute -> getString(R.string.theme_screen_title)
                        Destinations.AccessibilityRoute -> getString(R.string.accessibility_title)
                        Destinations.FoundationRoute -> getString(R.string.foundation_screen_title)
                        else -> ""
                    }

                    shouldShowBackNav = Destinations.BackNavigationScreen.contains(destination.route)
                }
            }

            AppDemoTheme(
                systemUiController = systemUiController,
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = title) },
                            navigationIcon = if (shouldShowBackNav) {
                                {
                                    IconButton(onClick = directions.navigateToPreviousScreen) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_back),
                                            contentDescription = null,
                                        )
                                    }
                                }
                            } else {
                                { }
                            },
                        )
                    },
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .padding(paddingValues = paddingValues),
                    ) {
                        MainNavGraph(
                            navController = navController,
                            directions = directions,
                        )
                    }
                }
            }
        }
    }
}
