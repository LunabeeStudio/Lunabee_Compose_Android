package studio.lunabee.compose.material

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import studio.lunabee.compose.R
import studio.lunabee.compose.material.common.screen.MenuBackScreen
import studio.lunabee.compose.material.theme.LunabeeComposeMaterialTheme
import studio.lunabee.compose.model.MenuEntry
import studio.lunabee.compose.navigation.ToDirection

/**
 * A list of elements to all Composable module displayable for [androidx.compose.material] only.
 *
 * @param navigateToPreviousScreen back navigation
 *
 * @param navigateToTopAppBarScreen screen for [studio.lunabee.compose.lbctopappbar] module
 */
@Composable
fun MaterialScreen(
    navigateToPreviousScreen: ToDirection,
    navigateToTopAppBarScreen: ToDirection,
) {
    val menus: List<MenuEntry> = remember {
        listOf(
            MenuEntry(
                titleRes = R.string.top_bar_list_title,
                subtitleRes = R.string.top_bar_list_subtitle,
                direction = navigateToTopAppBarScreen,
            ),
        )
    }

    MenuBackScreen(
        title = stringResource(id = R.string.material_title),
        menus = menus,
        navigateToPreviousScreen = navigateToPreviousScreen,
    )
}

@Preview
@Composable
private fun MaterialScreenPreview() {
    LunabeeComposeMaterialTheme {
        MaterialScreen(
            navigateToPreviousScreen = { },
            navigateToTopAppBarScreen = { },
        )
    }
}
