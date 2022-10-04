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
 * BarDescription.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/3/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbcgraph.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Describe how to draw the bar in the graph.
 * @param ordinateValue will determine the height of the bar depending on your ordinate axis.
 * @param defaultColor [Color] of the bar for normal state.
 * @param thickness thickness in [Dp] of the bar.
 */
data class BarDescription(
    val ordinateValue: Float,
    val defaultColor: Color,
    val thickness: Dp,
)
