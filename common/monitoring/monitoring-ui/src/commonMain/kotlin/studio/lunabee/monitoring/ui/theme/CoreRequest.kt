/*
 * Copyright (c) 2026 Lunabee Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package studio.lunabee.monitoring.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import studio.lunabee.monitoring.core.LBRequest
import studio.lunabee.monitoring.ui.res.CoreDrawable
import studio.lunabee.monitoring.ui.res.CoreString
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.offsetAt
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import studio.lunabee.monitoring.ui.res.ic_chevron_right
import studio.lunabee.monitoring.ui.res.networkRequestDuration
import studio.lunabee.monitoring.ui.res.networkRequestListSendingAt
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun CoreRequest(
    request: LBRequest,
    config: studio.lunabee.monitoring.ui.theme.CoreRequestConfig,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(space = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = request.methodName,
                    color = Color.Black,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(color = Color.LightGray)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = request.statusCode.toString(),
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(color = if (request.statusCode in 200..299) Color(0xFF16C47F) else Color(0xFFF93827))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Text(
                text = stringResource(
                    resource = _root_ide_package_.studio.lunabee.monitoring.ui.res.CoreString.networkRequestDuration,
                    if (request.duration < 500.milliseconds) "🚀" else "🐌",
                    request.duration.inWholeMilliseconds,
                ),
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column(
            modifier = Modifier
                .weight(weight = 1f),
        ) {
            Text(
                text = request.path ?: request.host,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = if (config.ellipsizeUrl) 1 else Int.MAX_VALUE,
                overflow = TextOverflow.Ellipsis,
            )
            if (config.areQueryParametersDisplayed && request.queryParams?.isNotEmpty() == true) {
                Text(
                    text = request.queryParams.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = if (config.ellipsizeUrl) 1 else Int.MAX_VALUE,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Text(
                text = stringResource(
                    resource = _root_ide_package_.studio.lunabee.monitoring.ui.res.CoreString.networkRequestListSendingAt,
                    request.sendingAt.format(DateTimeComponents.Formats.RFC_1123),
                ),
                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (config.showCurrentTimeZoneDate) {
                Text(
                    text = stringResource(
                        resource = _root_ide_package_.studio.lunabee.monitoring.ui.res.CoreString.networkRequestListSendingAt,
                        request.sendingAt.format(
                            DateTimeComponents.Formats.RFC_1123,
                            TimeZone.currentSystemDefault().offsetAt(Clock.System.now()),
                        ),
                    ),
                    style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        if (config.isChevronDisplayed) {
            Icon(
                painter = painterResource(resource = _root_ide_package_.studio.lunabee.monitoring.ui.res.CoreDrawable.ic_chevron_right),
                contentDescription = null,
            )
        }
    }
}
