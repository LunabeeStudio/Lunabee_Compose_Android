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
    KoinIsolatedContext(context = UiMonitoringIsolatedContext.getSafeKoinApp()) {
        val rootNavController: NavHostController = rememberNavController()
        MaterialTheme(
            colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
        ) {
            Surface {
                NavHost(
                    navController = rootNavController,
                    startDestination = NetworkRequestListDestination,
                    modifier = modifier,
                ) {
                    NetworkRequestListDestination.composable(
                        navGraphBuilder = this,
                        navScope = object : NetworkRequestListNavScope {
                            override val navigateBack: () -> Unit = closeMonitoring
                            override val navigateToRequestDetail: (requestId: Uuid) -> Unit = { requestId ->
                                rootNavController.navigate(NetworkRequestDetailDestination(requestId = requestId.toHexString()))
                            }
                        },
                    )
                    NetworkRequestDetailDestination.composable(
                        navGraphBuilder = this,
                        navScope = object : NetworkRequestDetailNavScope {
                            override val navigateBack: () -> Unit = { rootNavController.popBackStack() }
                        },
                    )
                }
            }
        }
    }
}
