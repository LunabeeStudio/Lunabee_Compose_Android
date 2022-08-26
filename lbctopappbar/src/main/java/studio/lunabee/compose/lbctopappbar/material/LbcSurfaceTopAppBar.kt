/*
 * Copyright © 2022 Lunabee Studio
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
 * LbcSurfaceTopAppBar.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 7/17/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbctopappbar.material

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A default [Surface] that will wrap any [androidx.compose.material.TopAppBar] content and will handle background content
 * below statusBar if needed, and elevation depending on your scrolling state or the value you provide.
 * @param modifier a custom [Modifier]
 * @param statusBarColor color that will be displayed on the status bar.
 * @param elevation elevation of the whole surface content. Set to 0 by default.
 * @param topAppBar content of your [androidx.compose.material.TopAppBar]
 */
@Composable
fun LbcSurfaceTopAppBar(
    modifier: Modifier = Modifier,
    statusBarColor: Color = if (isSystemInDarkTheme()) MaterialTheme.colors.surface else MaterialTheme.colors.primary,
    elevation: Dp = 0.dp,
    topAppBar: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = statusBarColor,
        elevation = elevation,
        content = topAppBar,
    )
}
