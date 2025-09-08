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
 * GlanceDensityExt.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 8/9/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.glance.extensions

import android.util.DisplayMetrics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.core.util.TypedValueCompat

/**
 * As LocalDensity is not available in Glance context, use the following method to convert
 * [TextUnit] to [Float] with the current device's display metrics.
 */
fun TextUnit.toPx(displayMetrics: DisplayMetrics): Float =
    TypedValueCompat.spToPx(value, displayMetrics)

/**
 * As LocalDensity is not available in Glance context, use the following method to convert
 * [Dp] to [Float] with the current device's display metrics.
 */
fun Dp.toPx(displayMetrics: DisplayMetrics): Float = TypedValueCompat.dpToPx(value, displayMetrics)
