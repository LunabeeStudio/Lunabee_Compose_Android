package studio.lunabee.compose.material.common.screen

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import studio.lunabee.compose.R
import studio.lunabee.compose.extension.topAppBarElevation
import studio.lunabee.compose.lbctopappbar.material.LbcNavigationTopAppBar
import studio.lunabee.compose.material.common.section.MenuSection
import studio.lunabee.compose.material.preview.PreviewMenuEntryParameter
import studio.lunabee.compose.material.theme.LunabeeComposeMaterialTheme
import studio.lunabee.compose.model.MenuEntry
import studio.lunabee.compose.navigation.ToDirection

/**
 * Screen with a simple [MenuSection] that displays a list of clickable item, with a [LbcNavigationTopAppBar].
 *
 * @param title title to set in the [LbcNavigationTopAppBar]
 *
 * @param menus items element to me displayed in the list
 *
 * @param navigateToPreviousScreen action on [LbcNavigationTopAppBar] navigation pressed
 */
@Composable
fun MenuBackScreen(
    title: String,
    menus: List<MenuEntry>,
    navigateToPreviousScreen: ToDirection,
) {
    val lazyListState: LazyListState = rememberLazyListState()

    Scaffold(
        topBar = {
            LbcNavigationTopAppBar(
                title = title,
                navigationIconRes = R.drawable.ic_back,
                elevation = lazyListState.topAppBarElevation,
                onNavigationClicked = navigateToPreviousScreen,
                backgroundColor = MaterialTheme.colors.surface,
                applyStatusBarPadding = true,
            )
        }
    ) {
        MenuSection(
            lazyListState = lazyListState,
            menus = menus,
        )
    }
}

@Preview
@Composable
private fun MenuBackScreenPreview() {
    LunabeeComposeMaterialTheme {
        MenuBackScreen(
            title = "TopAppBar",
            menus = PreviewMenuEntryParameter().values.toList(),
            navigateToPreviousScreen = { },
        )
    }
}
