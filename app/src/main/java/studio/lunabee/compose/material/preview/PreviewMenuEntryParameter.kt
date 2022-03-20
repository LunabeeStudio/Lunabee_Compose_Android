package studio.lunabee.compose.material.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import studio.lunabee.compose.R
import studio.lunabee.compose.model.MenuEntry

class PreviewMenuEntryParameter: PreviewParameterProvider<MenuEntry> {
    override val values: Sequence<MenuEntry> = sequenceOf(
        MenuEntry(
            titleRes = R.string.top_bar_list_title,
            subtitleRes = R.string.top_bar_list_subtitle,
            direction = { },
        ),
    )
}
