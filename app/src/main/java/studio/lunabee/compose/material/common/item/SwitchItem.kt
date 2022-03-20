package studio.lunabee.compose.material.common.item

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import studio.lunabee.compose.material.theme.LunabeeComposeMaterialTheme

/**
 * Display a Switch item with. This composable is not in charge to handle check state.
 *
 * @param modifier custom [Modifier] if needed
 *
 * @param switchText text to displayed with the switch
 *
 * @param isCheckedByDefault set a default value to switch check state
 *
 * @param onCheckChange called when switch state changes
 */
@Composable
fun SwitchItem(
    modifier: Modifier = Modifier,
    switchText: String,
    isCheckedByDefault: Boolean,
    onCheckChange: (isChecked: Boolean) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = switchText,
        )
        Switch(
            checked = isCheckedByDefault,
            onCheckedChange = onCheckChange,
        )
    }
}

@Preview
@Composable
private fun SwitchItemCheckedPreview() {
    LunabeeComposeMaterialTheme {
        SwitchItem(
            switchText = "Switch",
            onCheckChange = { },
            isCheckedByDefault = true,
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SwitchItemNotCheckedPreview() {
    LunabeeComposeMaterialTheme {
        SwitchItem(
            switchText = "Switch",
            onCheckChange = { },
            isCheckedByDefault = false,
        )
    }
}
