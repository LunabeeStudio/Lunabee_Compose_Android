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
