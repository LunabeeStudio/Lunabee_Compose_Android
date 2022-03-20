package studio.lunabee.compose.navigation

import android.widget.Toast
import androidx.navigation.NavHostController

typealias ToDirection = () -> Unit

class Directions(navController: NavHostController) {
    val navigateToMaterialScreen: ToDirection = {
        navController.navigate(Destinations.MATERIAL_ROUTE) {
            popUpTo(Destinations.MAIN_ROUTE) { inclusive = false }
        }
    }

    val navigateToMaterial3Screen: ToDirection = {
        Toast.makeText(navController.context, "Not implemented yet!", Toast.LENGTH_LONG).show()
    }

    val navigateToTopAppBarScreen: ToDirection = {
        navController.navigate(Destinations.TOP_APP_BAR_ROUTE) {
            popUpTo(Destinations.MATERIAL_ROUTE) { inclusive = false }
        }
    }

    val navigateToPreviousScreen: ToDirection = {
        navController.popBackStack()
    }
}
