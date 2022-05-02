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
 * LbcLoadingNavigationTopAppBar.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbctopappbar.material

import androidx.annotation.DrawableRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import studio.lunabee.compose.lbctopappbar.material.error.ExceptionThrown

/**
 * A [TopAppBar] with a navigation action and a [LinearProgressIndicator] that can be displayed depending on your loading state.
 * You can also add an optional [Row] with an action menus.
 *
 * @throws [ExceptionThrown.LbcTopAppBarNoTitleException] if [title] is null and [titleComposable] is not override.
 *
 * @throws [ExceptionThrown.LbcTopAppBarNoNavigationException] if [navigationIconRes] and/or [onNavigationClicked] are null
 * and [navigationIconComposable] is not override
 *
 * @param modifier custom [Modifier] applied on root view. [TopAppBar] [Modifier] can't be edited.
 * If you need to do so, create your own [TopAppBar]
 *
 * @param applyStatusBarPadding if you want to add status bar padding in [TopAppBar] layout.
 * It can be useful to handle correctly status bar color when user is scrolling on the page content.
 * If set to true, status bar color will be the same as [TopAppBar] [backgroundColor] on scroll.
 * Otherwise, it's your responsibility to handle [TopAppBar] position.
 *
 * @param title a nullable string to set in the default [Text]. If title is set to null, [titleComposable] must be override.
 *
 * @param navigationIconRes icon to be displayed in the default [navigationIconComposable]
 *
 * @param backgroundColor override default color if needed. Default color is the same used by Google in [TopAppBar]
 * - primary color is used in light mode
 * - surface color is used in dark mode
 *
 * @param elevation custom elevation depending on your need. By default, [TopAppBar] is not lifted
 *
 * @param isLoading if set to true, a [LinearProgressIndicator] will be displayed at the bottom of the [TopAppBar]
 *
 * @param loaderIndicatorColor default color for the loader, set by default to the primary, like Google [LinearProgressIndicator]
 *
 * @param navigationContentDescription provide a content description for your navigation icon. This parameter is not marked as mandatory
 * but you should provide an accessibility text for this type of action, as Google recommends.
 *
 * @param onNavigationClicked provide an action that will be execute on click of [navigationIconComposable]
 *
 * @param rowActionsComposable action menu to be displayed at the end of the [TopAppBar]. Your content will be part of a [RowScope],
 * so no need to wrap it into a [Row] when calling [LbcLoadingNavigationTopAppBar]
 *
 * @param titleComposable if you need to do customize title differently than the implementation provided by [LbcLoadingNavigationTopAppBar],
 * you can override [titleComposable] with your own implementation. By default, [titleComposable] is a simple text.
 * Default implementation of [titleComposable] should never be edited.
 *
 * @param navigationIconComposable if you need to customize navigation icon differently than the default implementation provided by
 * [LbcLoadingNavigationTopAppBar], you can override [navigationIconComposable] with your own implementation.
 * By default, [navigationIconComposable] is a simple [IconButton] set with the provided [navigationIconRes] and
 * [navigationContentDescription]. Default implementation of [navigationIconComposable] should never be edited
 *
 * @param showMenuOnLoading if set to true, menu will be displayed when [LinearProgressIndicator] is displayed. Otherwise, it will
 * be hidden.
 */
@Composable
fun LbcLoadingNavigationTopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    isLoading: Boolean = false,
    showMenuOnLoading: Boolean = true,
    applyStatusBarPadding: Boolean = false,
    @DrawableRes navigationIconRes: Int? = null,
    backgroundColor: Color = if (isSystemInDarkTheme()) MaterialTheme.colors.surface else MaterialTheme.colors.primary,
    elevation: Dp = 0.dp,
    loaderIndicatorColor: Color = MaterialTheme.colors.primary,
    navigationContentDescription: String? = null,
    onNavigationClicked: (() -> Unit)? = null,
    rowActionsComposable: (@Composable RowScope.() -> Unit)? = null,
    titleComposable: @Composable () -> Unit = { Text(text = title ?: throw ExceptionThrown.LbcTopAppBarNoTitleException) },
    navigationIconComposable: @Composable () -> Unit = {
        IconButton(onClick = onNavigationClicked ?: throw ExceptionThrown.LbcTopAppBarNoNavigationException) {
            Icon(
                painter = painterResource(id = navigationIconRes ?: throw ExceptionThrown.LbcTopAppBarNoNavigationException),
                contentDescription = navigationContentDescription,
            )
        }
    },
) {
    Surface(
        modifier = modifier,
        color = backgroundColor, // [Surface] handles background of the [TopAppBar].
        elevation = elevation, // [Surface] handles elevation of the [TopAppBar].
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            val (topAppBarRef, linearProgressBarRef) = createRefs()
            TopAppBar(
                modifier = Modifier
                    .constrainAs(topAppBarRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .then(
                        if (applyStatusBarPadding) {
                            Modifier
                                .padding(paddingValues = WindowInsets.statusBars.asPaddingValues())
                        } else {
                            Modifier
                        }
                    ),
                title = titleComposable,
                backgroundColor = backgroundColor, // Default background of [TopAppBar] needs to be the same than the [Surface]
                elevation = 0.dp, // Elevation is handle by surface.
                actions = {
                    Row {
                        if ((showMenuOnLoading && isLoading) || !isLoading) {
                            rowActionsComposable?.invoke(this)
                        }
                    }
                },
                navigationIcon = navigationIconComposable,
            )

            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .constrainAs(linearProgressBarRef) {
                            bottom.linkTo(topAppBarRef.bottom)
                            start.linkTo(topAppBarRef.start)
                            end.linkTo(topAppBarRef.end)
                            width = Dimension.fillToConstraints
                        },
                    color = loaderIndicatorColor,
                )
            }
        }
    }
}
