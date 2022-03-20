package studio.lunabee.compose.material.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.SystemUiController

/**
 * Material theme.
 */
@Composable
fun LunabeeComposeMaterialTheme(
    isSystemInDarkTheme: Boolean = isSystemInDarkTheme(),
    systemUiController: SystemUiController? = null,
    content: @Composable () -> Unit,
) {
    val colors = if (isSystemInDarkTheme) {
        darkColors(
            primary = Color.White,
            primaryVariant = Color.White,
            secondary = Color.LightGray,
            secondaryVariant = Color.LightGray,
        )
    } else {
        lightColors(
            primary = Color.LightGray,
            primaryVariant = Color.LightGray,
            secondary = Color.Black,
            secondaryVariant = Color.Black,
        )
    }

    SideEffect {
        systemUiController?.setStatusBarColor(Color.Transparent, darkIcons = !isSystemInDarkTheme)
    }

    MaterialTheme(
        colors = colors,
        content = content,
    )
}
