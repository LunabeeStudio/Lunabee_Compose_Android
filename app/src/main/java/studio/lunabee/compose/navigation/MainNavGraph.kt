package studio.lunabee.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.systemuicontroller.SystemUiController
import studio.lunabee.compose.MainScreen
import studio.lunabee.compose.material.MaterialScreen
import studio.lunabee.compose.material.topappbar.TopAppBarScreen
import studio.lunabee.compose.material.theme.LunabeeComposeMaterialTheme

@Composable
fun MainNavGraph(
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
            route = Destinations.TOP_APP_BAR_ROUTE,
        ) {
            LunabeeComposeMaterialTheme(
                systemUiController = systemUiController,
            ) {
                TopAppBarScreen(
                    navigateToPreviousScreen = directions.navigateToPreviousScreen,
                )
            }
        }
    }
}
