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

package com.lunabee.lbloading.hilt

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lunabee.lbloading.BlockingViewScope
import com.lunabee.lbloading.DefaultBlockingContent
import com.lunabee.lbloading.DefaultLoadingContent
import com.lunabee.lbloading.LoadingManager
import com.lunabee.lbloading.LoadingViewScope

/**
 * Add an empty [BackHandler] to block back action according to [LoadingManager] state
 */
@Composable
fun LoadingBlockBackView() {
    com.lunabee.lbloading.LoadingBlockBackView(loadingManager = rememberLoadingManager())
}

/**
 * Show a loading spinner according to [LoadingManager] state
 *
 * @param blockBackPress true block the back press, false to only show the loading
 * @param loadingContent the actual loading composable to show
 */
@Composable
fun LoadingView(
    contentDescription: String?,
    modifier: Modifier = Modifier,
    blockBackPress: Boolean = true,
    blockingContent: @Composable (BlockingViewScope.() -> Unit) = { DefaultBlockingContent() },
    loadingContent: @Composable (LoadingViewScope.() -> Unit) = { DefaultLoadingContent() },
) {
    com.lunabee.lbloading.LoadingView(
        contentDescription = contentDescription,
        loadingManager = rememberLoadingManager(),
        modifier = modifier,
        blockBackPress = blockBackPress,
        blockingContent = blockingContent,
        loadingContent = loadingContent,
    )
}
