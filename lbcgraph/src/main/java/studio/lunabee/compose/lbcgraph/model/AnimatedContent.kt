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
 * AnimatedContent.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/3/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbcgraph.model

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D

/**
 * Store data to draw bar correctly and animate it.
 * @param graphItemIndex index corresponding to the parent graph item.
 * @param topValue max value for current graph item
 * @param currentFraction value between 0.0f and 1.0f that will be used to draw the line.
 * @param animatable use to animate drawing.
 */
data class AnimatedContent(
    val graphItemIndex: Int,
    val topValue: Float,
    val currentFraction: Float,
    val animatable: Animatable<Float, AnimationVector1D>,
)
