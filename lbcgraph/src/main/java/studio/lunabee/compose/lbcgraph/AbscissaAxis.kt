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
 * AbscissaAxis.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/3/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbcgraph

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import studio.lunabee.compose.lbcgraph.model.AbscissaDetailInView

/**
 * Simple abscissa axis.
 * @param labels label to fill the axis [List][String].
 * @param onAbscissaPositioned callback triggered when abscissas are positioned in view. This will be use for content position.
 * @param modifier [Modifier].
 * @param defaultTextStyle text style for label.
 * @param abscissaPadding padding between each abscissa.
 */
@Composable
fun AbscissaAxis(
    labels: List<String>,
    onAbscissaPositioned: (abscissaDetailInView: Set<AbscissaDetailInView>) -> Unit,
    modifier: Modifier = Modifier,
    defaultTextStyle: TextStyle = androidx.compose.material.MaterialTheme.typography.body1,
    abscissaPadding: PaddingValues = PaddingValues(all = 0.dp),
) {
    val abscissaDetailInView: MutableSet<AbscissaDetailInView> = mutableSetOf()

    // Will avoid useless recomposition as onGloballyPositioned is called on scroll.
    var isAbscissaDetailInViewAlreadyComputed: Boolean by remember { mutableStateOf(value = false) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        labels.forEachIndexed { index, label ->
            val boxModifier = Modifier
                .onGloballyPositioned { layoutCoordinates ->
                    abscissaDetailInView += AbscissaDetailInView(
                        position = index,
                        width = layoutCoordinates.size.width,
                        height = layoutCoordinates.size.height,
                    )

                    if (!isAbscissaDetailInViewAlreadyComputed && abscissaDetailInView.size == labels.size) {
                        isAbscissaDetailInViewAlreadyComputed = true
                        onAbscissaPositioned(abscissaDetailInView)
                    }
                }
                .padding(paddingValues = abscissaPadding) // Order is important here to have correct details in onGloballyPositioned.

            Box(
                modifier = boxModifier,
            ) {
                Text(
                    text = label,
                    style = defaultTextStyle,
                )
            }
        }
    }
}
