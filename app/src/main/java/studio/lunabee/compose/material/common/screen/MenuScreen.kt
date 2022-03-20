package studio.lunabee.compose.material.common.screen

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import studio.lunabee.compose.extension.topAppBarElevation
import studio.lunabee.compose.lbctopappbar.material.LbcTopAppBar
import studio.lunabee.compose.material.common.section.MenuSection
import studio.lunabee.compose.material.preview.PreviewMenuEntryParameter
import studio.lunabee.compose.material.theme.LunabeeComposeMaterialTheme
import studio.lunabee.compose.model.MenuEntry

/**
 * Simple screen with a list of clickable item and a [LbcTopAppBar]
 *
 * @param title text to set in the [LbcTopAppBar]
 *
 * @param menus item to display
 */
@Composable
fun MenuScreen(
    title: String,
    menus: List<MenuEntry>,
) {
    val lazyListState: LazyListState = rememberLazyListState()

    Scaffold(
        topBar = {
            LbcTopAppBar(
                title = title,
                elevation = lazyListState.topAppBarElevation,
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
private fun MenuScreenPreview() {
    LunabeeComposeMaterialTheme {
        MenuScreen(
            title = "TopAppBar",
            menus = PreviewMenuEntryParameter().values.toList(),
        )
    }
}
