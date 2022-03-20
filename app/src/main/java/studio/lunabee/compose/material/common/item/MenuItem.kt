package studio.lunabee.compose.material.common.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import studio.lunabee.compose.material.preview.PreviewMenuEntryParameter
import studio.lunabee.compose.material.theme.LunabeeComposeMaterialTheme
import studio.lunabee.compose.model.MenuEntry

/**
 * Item build on [MenuEntry] that will be displayed in [studio.lunabee.compose.material.common.section.MenuSection].
 *
 * @param menu a [MenuEntry] object
 */
@Composable
fun MenuItem(
    menu: MenuEntry,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { menu.direction() }
            .padding(horizontal = 16.dp)
            .height(height = 56.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = menu.titleRes),
        )
        Text(
            text = stringResource(id = menu.subtitleRes),
            style = MaterialTheme.typography.caption,
        )
    }
}

@Preview
@Composable
private fun MenuItemPreview() {
    LunabeeComposeMaterialTheme {
        MenuItem(
            menu = PreviewMenuEntryParameter().values.first(),
        )
    }
}
