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
 * OrdinateAxis.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/3/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbcgraph

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Ordinate axis.
 * @param topValue max value of the graph
 * @param numberOfOrdinateValue scale of axis. If set to 4 for example, axis will have 4 values compute from [topValue] + 0
 * @param ordinateValueFormatter format a float value in a displayable string as you want (ex: 45.0 can become 45.00€).
 * @param topContentSafePadding top padding that will be use if you have a clickable zone on graph content.
 * @param modifier [Modifier]
 * @param defaultTextStyle text style for ordinate label.
 */
@Composable
fun OrdinateAxis(
    topValue: Float,
    numberOfOrdinateValue: Int,
    ordinateValueFormatter: (value: Float) -> String,
    topContentSafePadding: Dp,
    modifier: Modifier = Modifier,
    defaultTextStyle: TextStyle = MaterialTheme.typography.body1,
) {
    val scale = topValue / numberOfOrdinateValue
    val ordinateEntries: MutableList<Float> = mutableListOf()
    var currentValue = 0.0f
    while (currentValue < topValue) {
        ordinateEntries += currentValue
        currentValue += scale
    }
    ordinateEntries += topValue

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        ordinateEntries.sortedDescending().forEachIndexed { index, label ->
            Text(
                text = ordinateValueFormatter(label),
                style = defaultTextStyle,
                modifier = if (index == 0) {
                    Modifier.padding(top = topContentSafePadding)
                } else {
                    Modifier
                },
            )
        }
    }
}
