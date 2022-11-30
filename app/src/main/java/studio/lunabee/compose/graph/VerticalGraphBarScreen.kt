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
 * VerticalGraphBarScreen.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/3/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.graph

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import studio.lunabee.compose.R
import studio.lunabee.compose.extension.topAppBarElevation
import studio.lunabee.compose.lbcgraph.VerticalBarGraph
import studio.lunabee.compose.lbcgraph.model.BarDescription
import studio.lunabee.compose.lbcgraph.model.GraphBarItem
import studio.lunabee.compose.lbcgraph.model.GraphConfiguration
import studio.lunabee.compose.lbcgraph.model.OnBarClicked
import studio.lunabee.compose.lbctopappbar.material.LbcTopAppBar
import studio.lunabee.compose.lbctopappbar.material.model.LbcTopAppBarAction
import studio.lunabee.compose.navigation.ToDirection
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.random.Random

@Composable
fun VerticalBarGraphScreen(
    navigateToPreviousScreen: ToDirection,
) {
    val lazyListState: LazyListState = rememberLazyListState()
    var selectedBarId: Any? by rememberSaveable { mutableStateOf(value = null) }

    val abscissaAxisLabel = generateAbscissaLabels()
    val graphBarItems = generateData()

    Scaffold(
        topBar = {
            LbcTopAppBar(
                title = stringResource(id = R.string.vertical_bar_graph_screen_title),
                elevation = lazyListState.topAppBarElevation,
                navigationAction = LbcTopAppBarAction.DrawableResAction(
                    iconRes = R.drawable.ic_back,
                    contentDescription = null,
                    action = navigateToPreviousScreen,
                ),
                applyStatusBarPadding = true,
                topAppBarBackgroundColor = MaterialTheme.colors.surface,
            )
        },
    ) {
        Column {
            LazyColumn(
                state = lazyListState,
                contentPadding = it,
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = 300.dp)
                            .padding(all = 16.dp),
                    ) {
                        VerticalBarGraph(
                            abscissaAxisLabel = abscissaAxisLabel,
                            graphBarItems = graphBarItems,
                            graphConfiguration = GraphConfiguration(
                                abscissaPadding = PaddingValues(all = 8.dp),
                                startPaddingBetweenOrdinateAndContent = 8.dp,
                                numberOfOrdinateValue = 3,
                                ordinateValueFormatter = { value ->
                                    String.format(locale = Locale.getDefault(), "%.2f", value) + "€"
                                },
                                onBarClicked = OnBarClicked(
                                    onBarClicked = { selectedBarId = it },
                                    clickLabel = null,
                                ),
                            ),
                            modifier = Modifier
                                .padding(all = 16.dp)
                                .fillMaxSize(),
                            barIdSelected = selectedBarId,
                        )
                    }
                }

                item {
                    val values = graphBarItems.firstOrNull() { it.id == selectedBarId }?.content?.sortedBy { it.ordinateValue }
                        ?.map { it.ordinateValue }
                    Text(
                        text = values?.toString() ?: "No selection",
                        modifier = Modifier
                            .padding(all = 16.dp),
                    )
                }
            }
        }
    }
}

private fun generateAbscissaLabels(): List<String> {
    val monthLabels: MutableList<String> = mutableListOf()
    val formatter = DateTimeFormatter.ofPattern("MMM")
    for (i in 1..12) {
        val date = LocalDate.of(2022, i, 1)
        val monthValue = formatter.format(date)
        monthLabels += monthValue.replaceFirstChar { it.uppercase() }
    }
    return monthLabels
}

@Suppress("UnusedPrivateMember")
private fun generateData(): List<GraphBarItem> {
    val randomData: MutableList<GraphBarItem> = mutableListOf()
    for (i in 1..12) {
        val randomDataBarDescription: MutableList<BarDescription> = mutableListOf()
        var sum = 0
        for (j in 1..5) {
            val randomColor = Color(Random.nextInt(until = 256), Random.nextInt(until = 256), Random.nextInt(until = 256))
            val nextInt = Random.nextInt(from = 1, until = 100)
            sum += nextInt
            if (sum > 100 || randomDataBarDescription.firstOrNull { it.ordinateValue == nextInt.toFloat() } != null) continue
            randomDataBarDescription += BarDescription(
                ordinateValue = nextInt.toFloat(),
                defaultColor = randomColor,
                thickness = 10.dp,
            )
        }
        randomData += GraphBarItem(
            id = i,
            content = randomDataBarDescription,
        )
    }
    return randomData
}
