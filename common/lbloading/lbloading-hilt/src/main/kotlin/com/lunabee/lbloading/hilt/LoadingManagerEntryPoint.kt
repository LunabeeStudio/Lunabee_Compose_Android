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
