package studio.lunabee.monitoring.ui.theme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import studio.lunabee.monitoring.core.LBPayload
import studio.lunabee.monitoring.ui.res.CoreDrawable
import studio.lunabee.monitoring.ui.res.CoreString
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import studio.lunabee.monitoring.ui.res.ic_chevron_right
import studio.lunabee.monitoring.ui.res.networkRequestDetailBody
import studio.lunabee.monitoring.ui.res.networkRequestDetailContentLengthValue
import studio.lunabee.monitoring.ui.res.networkRequestDetailHeaders
import studio.lunabee.monitoring.ui.res.networkRequestDetailNoBody
import studio.lunabee.monitoring.ui.res.networkRequestDetailNoHeader

@Composable
internal fun CorePayload(
    payload: LBPayload,
    title: StringResource,
    modifier: Modifier = Modifier,
) {
    var isExpanded: Boolean by rememberSaveable { mutableStateOf(false) }
    val rotation = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isExpanded = !isExpanded
                    coroutineScope.launch {
                        rotation.animateTo(targetValue = if (isExpanded) 90f else 0f)
                    }
                }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(weight = 1f),
            ) {
                Text(
                    text = stringResource(resource = title),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .fillMaxWidth(),
                )
                Text(
                    text = stringResource(
                        resource = CoreString.networkRequestDetailContentLengthValue,
                        if (payload.size > 500) "🍔" else "🥦",
                        payload.size,
                    ),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Icon(
                painter = painterResource(resource = CoreDrawable.ic_chevron_right),
                contentDescription = null,
                modifier = Modifier
                    .rotate(degrees = rotation.value),
            )
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
            ) {
                Text(
                    text = stringResource(resource = CoreString.networkRequestDetailHeaders),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                )
                Text(
                    text = payload.headers?.split(";")?.joinToString("\n")
                        ?: stringResource(resource = CoreString.networkRequestDetailNoHeader),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(height = 8.dp))
                Text(
                    text = stringResource(resource = CoreString.networkRequestDetailBody),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                )
                Text(
                    text = payload.body ?: stringResource(resource = CoreString.networkRequestDetailNoBody),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}
