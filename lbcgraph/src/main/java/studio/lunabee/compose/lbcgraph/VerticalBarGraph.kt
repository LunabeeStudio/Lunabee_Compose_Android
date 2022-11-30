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
 * VerticalBarGraph.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/3/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbcgraph

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import studio.lunabee.compose.lbcgraph.model.AbscissaDetailInView
import studio.lunabee.compose.lbcgraph.model.GraphBarItem
import studio.lunabee.compose.lbcgraph.model.GraphConfiguration

/**
 * Standard vertical bar graph.
 * @param abscissaAxisLabel label for abscissa [List][String].
 * @param graphBarItems describe each bar of your graph. An item can contains multiple bar (or just once).
 * @param graphConfiguration configuration for graph, see [GraphConfiguration].
 * @param modifier [Modifier].
 * @param barIdSelected current bar selected, can be null. Will only be used if [GraphConfiguration.onBarClicked] is provided.
 */
@Composable
fun VerticalBarGraph(
    abscissaAxisLabel: List<String>,
    graphBarItems: List<GraphBarItem>,
    graphConfiguration: GraphConfiguration,
    modifier: Modifier = Modifier,
    barIdSelected: Any? = null,
) {
    // Position in view of each abscissa. Will be used by graph content for position.
    var abscissaDetailsInView: Set<AbscissaDetailInView> by remember { mutableStateOf(value = setOf()) }

    // Scroll shared between abscissa and graph content.
    val sharedScrollState = rememberScrollState()

    // Max value depending on data provided by user.
    val ordinateTopValue = graphBarItems
        .maxOf { graphBarItem -> graphBarItem.content.maxOf { barDescription -> barDescription.ordinateValue } }

    VerticalBaseGraph(
        startPaddingBetweenOrdinateAndContent = graphConfiguration.startPaddingBetweenOrdinateAndContent,
        modifier = modifier,
        abscissaAxis = {
            AbscissaAxis(
                labels = abscissaAxisLabel,
                onAbscissaPositioned = { abscissaDetailInView ->
                    abscissaDetailsInView = abscissaDetailInView
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(state = sharedScrollState),
                abscissaPadding = graphConfiguration.abscissaPadding,
            )
        },
        ordinateAxis = {
            OrdinateAxis(
                topValue = ordinateTopValue,
                numberOfOrdinateValue = graphConfiguration.numberOfOrdinateValue,
                ordinateValueFormatter = graphConfiguration.ordinateValueFormatter,
                modifier = Modifier
                    .fillMaxHeight(),
                topContentSafePadding = graphConfiguration.topContentSafePadding,
            )
        },
        graphContent = {
            BarGraphContent(
                graphBarItems = graphBarItems,
                abscissasDetailInView = abscissaDetailsInView.toList(),
                ordinateTopValue = ordinateTopValue,
                barAnimation = graphConfiguration.barAnimation,
                clickableZoneShape = graphConfiguration.clickableZoneShape,
                clickableZonePaddingValues = graphConfiguration.clickableZonePaddingValues,
                topContentSafePadding = graphConfiguration.topContentSafePadding,
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(state = sharedScrollState),
                onBarClicked = graphConfiguration.onBarClicked,
                barIdSelected = barIdSelected,
            )
        },
    )
}
