package com.lunabee.lbloading

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * Wrapper around [BackHandler] compliant with the global loading view by using the [LoadingManager]
 * @see BackHandler
 *
 * @param enabled if this BackHandler should be enabled when there is no loading
 * @param onBack the action invoked by pressing the system back when there is no loading
 */
@Composable
fun LoadingBackHandler(
    loadingManager: LoadingManager,
    enabled: Boolean = true,
    onBack: () -> Unit,
) {
    val loadingState = loadingManager.loadingState.collectAsStateWithLifecycle()
    val isBlocking = loadingState.value.isBlocking
    BackHandler(enabled || isBlocking) {
        if (!isBlocking) {
            onBack()
        }
    }
}
