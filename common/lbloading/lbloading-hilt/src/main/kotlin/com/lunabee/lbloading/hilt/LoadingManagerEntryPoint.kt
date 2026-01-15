package com.lunabee.lbloading.hilt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.lunabee.lbloading.LoadingManager
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt [EntryPoint] to access [LoadingManager] outside of Hilt components
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface LoadingManagerEntryPoint {
    val loadingManager: LoadingManager
}

/**
 * Remember the [LoadingManager] singleton to manage global loading from compose
 */
@Composable
fun rememberLoadingManager(): LoadingManager {
    val applicationContext = LocalContext.current.applicationContext
    return remember(applicationContext) {
        EntryPoints.get(applicationContext, LoadingManagerEntryPoint::class.java).loadingManager
    }
}
