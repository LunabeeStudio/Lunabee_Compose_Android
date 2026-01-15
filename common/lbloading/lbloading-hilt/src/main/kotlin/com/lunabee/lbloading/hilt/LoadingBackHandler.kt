package com.lunabee.lbloading.hilt

import androidx.compose.runtime.Composable

/**
 * Wrapper around [BackHandler] compliant with the global loading view by using the [LoadingManager]
 * @see BackHandler
 *
 * @param enabled if this BackHandler should be enabled when there is no loading
 * @param onBack the action invoked by pressing the system back when there is no loading
 */
@Composable
fun LoadingBackHandler(
    enabled: Boolean = true,
    onBack: () -> Unit,
) {
    com.lunabee.lbloading.LoadingBackHandler(
        loadingManager = rememberLoadingManager(),
        enabled = enabled,
        onBack = onBack,
    )
}
