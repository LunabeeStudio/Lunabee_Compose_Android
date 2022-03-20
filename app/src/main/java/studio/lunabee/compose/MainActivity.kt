package studio.lunabee.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import studio.lunabee.compose.navigation.Directions
import studio.lunabee.compose.navigation.MainNavGraph

/**
 * Content of [MainActivity] uses [androidx.compose.material].
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // For status bar color with elevation.
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()
            val systemUiController = rememberSystemUiController()
            val directions = remember {
                Directions(
                    navController = navController,
                )
            }

            Box(
                modifier = Modifier.navigationBarsPadding(),
            ) {
                MainNavGraph(
                    navController = navController,
                    systemUiController = systemUiController,
                    directions = directions,
                )
            }
        }
    }
}
