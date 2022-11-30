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
 * BarGraphContent.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/3/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbcgraph

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import studio.lunabee.compose.lbcgraph.model.AbscissaDetailInView
import studio.lunabee.compose.lbcgraph.model.AnimatedContent
import studio.lunabee.compose.lbcgraph.model.GraphBarItem
import studio.lunabee.compose.lbcgraph.model.OnBarClicked

/**
 * Draw line depending on data provided.
 * @param graphBarItems details of the graph that will be used to draw bars. See [GraphBarItem].
 * @param abscissasDetailInView abscissa position. Size must be the same as [graphBarItems]
 * @param ordinateTopValue top value for ordinate that will be used to draw bars.
 * @param barAnimation animation launch at first display
 * @param clickableZoneShape ripple effect shape. Only used if [onBarClicked] is not null.
 * @param clickableZonePaddingValues padding values on clickable zone. Only used if [onBarClicked] is not null.
 * @param topContentSafePadding top padding use if [onBarClicked] is not null to give space to clickable zone.
 * @param modifier [Modifier].
 * @param onBarClicked enable click on the whole item // TODO make each sub bar clickable.
 * @param barIdSelected current selected bar. It will add a specific background.
 */
@Composable
fun BarGraphContent(
    graphBarItems: List<GraphBarItem>,
    abscissasDetailInView: List<AbscissaDetailInView>,
    ordinateTopValue: Float,
    barAnimation: AnimationSpec<Float>,
    clickableZoneShape: Shape,
    clickableZonePaddingValues: PaddingValues,
    topContentSafePadding: Dp,
    modifier: Modifier = Modifier,
    onBarClicked: OnBarClicked? = null,
    barIdSelected: Any? = null,
) {
    if (abscissasDetailInView.isNotEmpty() && graphBarItems.size == abscissasDetailInView.size) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // Precalculate animation values.
            val animatedContent = remember {
                val indexedAnimatedContentDetail: MutableMap<Int, List<AnimatedContent>> = mutableMapOf()
                graphBarItems.forEachIndexed { indexGraph, graphBarItems ->
                    val sortedByAscending = graphBarItems.content.sortedBy { it.ordinateValue }
                    if (sortedByAscending.isNotEmpty()) {
                        val maxOrdinateValueForCurrentAbscissa = sortedByAscending.maxOf { it.ordinateValue }
                        sortedByAscending.forEachIndexed { indexSort, barDescription ->
                            val previousValue = sortedByAscending.getOrNull(index = indexSort - 1)?.ordinateValue
                            val currentFraction = if (previousValue == null) {
                                barDescription.ordinateValue / maxOrdinateValueForCurrentAbscissa
                            } else {
                                (barDescription.ordinateValue - previousValue) / maxOrdinateValueForCurrentAbscissa
                            }
                            indexedAnimatedContentDetail[indexGraph] = indexedAnimatedContentDetail[indexGraph].orEmpty() + AnimatedContent(
                                graphItemIndex = indexGraph,
                                topValue = maxOrdinateValueForCurrentAbscissa / ordinateTopValue,
                                currentFraction = currentFraction,
                                animatable = Animatable(initialValue = 0.0f),
                            )
                        }
                    }
                }
                indexedAnimatedContentDetail.toList()
            }

            // Start animation for each line.
            animatedContent.forEach { (_, animation) ->
                animation.forEach { animatedContent ->
                    LaunchedEffect(key1 = Unit) {
                        animatedContent.animatable.animateTo(
                            targetValue = animatedContent.currentFraction,
                            animationSpec = barAnimation,
                        )
                    }
                }
            }

            graphBarItems.forEachIndexed { indexGraph, graphBarItem ->
                Box(
                    modifier = Modifier
                        .width(width = with(LocalDensity.current) { abscissasDetailInView[indexGraph].width.toDp() })
                        .fillMaxHeight()
                        .thenBackgroundModifier(
                            shouldBeApplied = graphBarItem.id == barIdSelected,
                            clickableZoneShape = clickableZoneShape,
                        )
                        .thenClickableModifier(
                            onBarClicked = onBarClicked,
                            clickableZoneShape = clickableZoneShape,
                            clickableZonePaddingValues = clickableZonePaddingValues,
                            graphBarItemId = graphBarItem.id,
                        )
                        .padding(bottom = with(LocalDensity.current) { abscissasDetailInView[indexGraph].height.toDp() }),
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    val sortedByAscending = graphBarItem.content.sortedBy { it.ordinateValue }
                    if (sortedByAscending.isNotEmpty()) {
                        Canvas(
                            modifier = Modifier
                                .padding(top = topContentSafePadding)
                                .fillMaxHeight(fraction = sortedByAscending.maxOf { it.ordinateValue } / ordinateTopValue),
                        ) {
                            var previousOffsetY = size.height
                            sortedByAscending.forEachIndexed { indexBar, barDescription ->
                                val animatedFractionValue = animatedContent[indexGraph].second[indexBar].animatable.value
                                drawPath(
                                    path = Path().apply {
                                        moveTo(x = 0.0f, y = previousOffsetY)
                                        lineTo(x = 0.0f, y = previousOffsetY - size.height * animatedFractionValue)
                                        close()
                                    },
                                    color = barDescription.defaultColor,
                                    style = Stroke(width = barDescription.thickness.toPx()),
                                )

                                previousOffsetY -= size.height * animatedFractionValue
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun Modifier.thenClickableModifier(
    onBarClicked: OnBarClicked?,
    graphBarItemId: Any,
    clickableZoneShape: Shape,
    clickableZonePaddingValues: PaddingValues,
) = if (onBarClicked == null) {
    this
} else {
    clip(shape = clickableZoneShape)
        .clickable(
            onClick = { onBarClicked.onBarClicked(graphBarItemId) },
            onClickLabel = onBarClicked.clickLabel,
            role = onBarClicked.role,
        )
        .padding(paddingValues = clickableZonePaddingValues)
}

private fun Modifier.thenBackgroundModifier(
    shouldBeApplied: Boolean,
    clickableZoneShape: Shape,
) = composed {
    if (shouldBeApplied) {
        val backgroundColor = RippleTheme.defaultRippleColor(
            contentColor = MaterialTheme.colors.primary,
            lightTheme = !isSystemInDarkTheme(),
        )

        clip(shape = clickableZoneShape)
            .background(
                color = Color(
                    red = backgroundColor.red,
                    green = backgroundColor.green,
                    blue = backgroundColor.blue,
                    alpha = RippleTheme.defaultRippleAlpha(
                        contentColor = MaterialTheme.colors.primary,
                        lightTheme = !isSystemInDarkTheme(),
                    ).pressedAlpha,
                ),
            )
    } else {
        this
    }
}
