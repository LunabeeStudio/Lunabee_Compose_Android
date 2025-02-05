/*
 * Copyright (c) 2024 Lunabee Studio
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
 *
 * GlanceRoot.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/16/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.glance.ui

import androidx.compose.runtime.Composable
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.layout.Box

/**
 * Apply automatically [appWidgetBackground] to your widget.
 */
@Composable
fun GlanceRoot(
    modifier: GlanceModifier = GlanceModifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = GlanceModifier
            .appWidgetBackground()
            .then(other = modifier),
        content = content,
    )
}
