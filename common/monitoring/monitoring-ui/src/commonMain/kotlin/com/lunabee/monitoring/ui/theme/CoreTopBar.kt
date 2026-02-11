package studio.lunabee.monitoring.ui.theme

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun CoreTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = { Text(title) },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = onBackClicked,
            ) {
                Icon(
                    painter = painterResource(BackButton),
                    contentDescription = "Navigate back",
                )
            }
        },
        actions = actions,
    )
}
