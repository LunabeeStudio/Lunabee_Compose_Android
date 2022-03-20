package studio.lunabee.compose.material.common.section

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import studio.lunabee.compose.material.common.item.MenuItem
import studio.lunabee.compose.material.preview.PreviewMenuEntryParameter
import studio.lunabee.compose.model.MenuEntry

/**
 * Build a [androidx.compose.foundation.lazy.LazyColumn] with provided items.
 *
 * @param lazyListState a remembered [androidx.compose.foundation.lazy.LazyListState]
 *
 * @param menus items to display
 */
@Composable
fun MenuSection(
    lazyListState: LazyListState,
    menus: List<MenuEntry>,
) {
    LazyColumn(
        state = lazyListState,
    ) {
        items(
            items = menus,
            key = { it.titleRes },
        ) { menu ->
            MenuItem(
                menu = menu,
            )
            Divider()
        }
    }
}

@Preview
@Composable
private fun MenuSectionPreview() {
    MenuSection(
        lazyListState = rememberLazyListState(),
        menus = PreviewMenuEntryParameter().values.toList(),
    )
}
