/*
 * Copyright (c) 2026 Lunabee Studio
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
 */

package studio.lunabee.monitoring.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import studio.lunabee.monitoring.ui.di.UiMonitoringIsolatedContext
import studio.lunabee.monitoring.ui.details.NetworkRequestDetailDestination
import studio.lunabee.monitoring.ui.details.NetworkRequestDetailNavScope
import studio.lunabee.monitoring.ui.list.NetworkRequestListDestination
import studio.lunabee.monitoring.ui.list.NetworkRequestListNavScope
import org.koin.compose.KoinIsolatedContext
import kotlin.uuid.Uuid

@Composable
fun LBMonitoringMainRoute(
    closeMonitoring: () -> Unit,
    modifier: Modifier = Modifier,
) {
    KoinIsolatedContext(context = _root_ide_package_.studio.lunabee.monitoring.ui.di.UiMonitoringIsolatedContext.getSafeKoinApp()) {
        val rootNavController: NavHostController = rememberNavController()
        MaterialTheme(
            colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
        ) {
            Surface {
                NavHost(
                    navController = rootNavController,
                    startDestination = _root_ide_package_.studio.lunabee.monitoring.ui.list.NetworkRequestListDestination,
                    modifier = modifier,
                ) {
                    _root_ide_package_.studio.lunabee.monitoring.ui.list.NetworkRequestListDestination.composable(
                        navGraphBuilder = this,
                        navScope = object : studio.lunabee.monitoring.ui.list.NetworkRequestListNavScope {
                            override val navigateBack: () -> Unit = closeMonitoring
                            override val navigateToRequestDetail: (requestId: Uuid) -> Unit = { requestId ->
                                rootNavController.navigate(
                                    _root_ide_package_.studio.lunabee.monitoring.ui.details.NetworkRequestDetailDestination(
                                        requestId = requestId.toHexString(),
                                    ),
                                )
                            }
                        },
                    )
                    _root_ide_package_.studio.lunabee.monitoring.ui.details.NetworkRequestDetailDestination.Companion.composable(
                        navGraphBuilder = this,
                        navScope = object : studio.lunabee.monitoring.ui.details.NetworkRequestDetailNavScope {
                            override val navigateBack: () -> Unit = { rootNavController.popBackStack() }
                        },
                    )
                }
            }
        }
    }
}
