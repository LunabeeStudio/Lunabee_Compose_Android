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
 * VerticalBaseGraph.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/3/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbcgraph

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

/**
 * Base graph than can be use to create a custom one. This graph is intended to be use as Vertical.
 * @param startPaddingBetweenOrdinateAndContent space between ordinate axis and content/abscissa in [Dp].
 * @param modifier [Modifier]
 * @param abscissaAxis [Composable] representing your abscissa axis.
 * @param ordinateAxis [Composable] representing your ordinate axis.
 * @param graphContent [Composable] representing your graph content.
 */
@Composable
fun VerticalBaseGraph(
    startPaddingBetweenOrdinateAndContent: Dp,
    modifier: Modifier = Modifier,
    abscissaAxis: @Composable () -> Unit,
    ordinateAxis: @Composable () -> Unit,
    graphContent: @Composable () -> Unit,
) {
    ConstraintLayout(
        modifier = modifier,
    ) {
        val (abscissaAxisRef, ordinateAxisRef, graphContentRef) = createRefs()
        Box(
            modifier = Modifier
                .constrainAs(ref = ordinateAxisRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(abscissaAxisRef.top)
                    width = Dimension.wrapContent
                    height = Dimension.fillToConstraints
                },
            content = { ordinateAxis() },
        )

        Box(
            modifier = Modifier
                .constrainAs(ref = abscissaAxisRef) {
                    start.linkTo(ordinateAxisRef.end, margin = startPaddingBetweenOrdinateAndContent)
                    end.linkTo(parent.end)
                    top.linkTo(ordinateAxisRef.bottom)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.preferredWrapContent
                },
            content = { abscissaAxis() },
        )

        Box(
            modifier = Modifier
                .constrainAs(ref = graphContentRef) {
                    start.linkTo(ordinateAxisRef.end, margin = startPaddingBetweenOrdinateAndContent)
                    bottom.linkTo(abscissaAxisRef.bottom)
                    end.linkTo(parent.end)
                    top.linkTo(ordinateAxisRef.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            content = { graphContent() },
        )
    }
}
