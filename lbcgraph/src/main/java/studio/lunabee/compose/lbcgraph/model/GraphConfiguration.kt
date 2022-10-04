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
 * GraphStyle.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/3/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbcgraph.model

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Configure graph style and action.
 * @param abscissaPadding padding between each abscissa item.
 * @param startPaddingBetweenOrdinateAndContent padding between ordinate axis and content/abscissa.
 * @param numberOfOrdinateValue scale for ordinate value.
 * @param barAnimation animation launch on first display when drawing bars.
 * @param onBarClicked add action on an item clicked.
 * @param clickableZoneShape ripple shape.
 * @param clickableZonePaddingValues ripple padding
 * @param topContentSafePadding additional padding between bar top and ripple top.
 */
data class GraphConfiguration(
    val abscissaPadding: PaddingValues,
    val startPaddingBetweenOrdinateAndContent: Dp,
    val numberOfOrdinateValue: Int,
    val ordinateValueFormatter: (value: Float) -> String,
    val barAnimation: AnimationSpec<Float> = tween(durationMillis = 600, easing = LinearEasing),
    val onBarClicked: OnBarClicked? = null,
    val clickableZoneShape: Shape = ClickableZoneShape,
    val clickableZonePaddingValues: PaddingValues = ClickableZonePaddingValues,
    val topContentSafePadding: Dp = TopContentSafePadding,
) {
    companion object DefaultValues {
        val TopContentSafePadding: Dp = 8.dp
        val ClickableZonePaddingValues: PaddingValues = PaddingValues(all = 8.dp)
        val ClickableZoneShape: Shape = RoundedCornerShape(size = 8.dp)
    }
}

/**
 * Describe click action.
 * @param onBarClicked action to execute. Get back id clicked.
 * @param clickLabel for accessibility purpose.
 * @param role for accessibility purpose.
 */
data class OnBarClicked(
    val onBarClicked: ((id: Any) -> Unit),
    val clickLabel: String?,
    val role: Role = Role.Button,
)
