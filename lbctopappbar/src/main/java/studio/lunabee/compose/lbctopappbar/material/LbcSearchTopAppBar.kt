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
 * LbcSearchTopAppBar.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 7/13/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbctopappbar.material

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import studio.lunabee.compose.lbctopappbar.material.internal.LbcTopAppBarUtils.applyStatusBarPaddingIfNeeded
import studio.lunabee.compose.lbctopappbar.material.model.LbcTopAppBarAction

/**
 * A simple [TopAppBar] with a [Text] title and a [LinearProgressIndicator] displayed when you want, at the bottom.
 * You can also add an optional [Row] with an action menus.
 *
 * @param title default title set in a [Text].
 * @param isSearchBarExpanded either display the search menu or the edit text.
 * @param searchAction action triggered when user clicks on menu icon. Should set [isSearchBarExpanded] to true.
 * @param modifier custom [Modifier] applied on root view. [TopAppBar] [Modifier] can't be edited.
 * If you need to do so, create your own [TopAppBar]
 * @param applyStatusBarPadding if you want to add status bar padding in [TopAppBar] layout.
 * It can be useful to handle correctly status bar color when user is scrolling on the page content.
 * If set to true, status bar color will be the same as [TopAppBar] [topAppBarBackgroundColor] on scroll.
 * @param topAppBarBackgroundColor override default color if needed. Default color is the same used by Google in [TopAppBar]
 * - primary color is used in light mode
 * - surface color is used in dark mode
 * @param statusBarColor color of the status bar. Will only be visible if [applyStatusBarPadding] is set to true.
 * @param elevation custom elevation depending on your need. By default, [TopAppBar] is not lifted
 * @param navigationAction if not null, it will add a navigation icon at the start.
 * @param rowActionsComposable action menu to be displayed at the end of the [TopAppBar]. Your content will be part of a [RowScope],
 * so no need to wrap it into a [Row]
 * @param isLoading flag that determines if [LinearProgressIndicator] should be displayed or not
 * @param showMenuOnLoading flag that determines if you want to hide menu when [isLoading] is true
 * @param loaderIndicatorColor color of [LinearProgressIndicator]
 * @param searchBarComposable your implementation of search bar content. You can use [LbcSearchTextField]
 */
@Composable
fun LbcSearchTopAppBar(
    title: String,
    isSearchBarExpanded: Boolean,
    searchAction: LbcTopAppBarAction,
    modifier: Modifier = Modifier,
    applyStatusBarPadding: Boolean = false,
    topAppBarBackgroundColor: Color = if (isSystemInDarkTheme()) MaterialTheme.colors.surface else MaterialTheme.colors.primary,
    statusBarColor: Color = topAppBarBackgroundColor,
    elevation: Dp = 0.dp,
    navigationAction: LbcTopAppBarAction? = null,
    rowActionsComposable: (@Composable RowScope.() -> Unit)? = null,
    isLoading: Boolean = false,
    showMenuOnLoading: Boolean = true,
    loaderIndicatorColor: Color = MaterialTheme.colors.primary,
    searchBarComposable: @Composable () -> Unit,
) {
    Box {
        LbcSurfaceTopAppBar(
            modifier = modifier,
            statusBarColor = statusBarColor,
            elevation = elevation,
        ) {
            TopAppBar(
                modifier = Modifier
                    .applyStatusBarPaddingIfNeeded(applyStatusBarPadding = applyStatusBarPadding),
                elevation = 0.dp, // handle by surface
                title = {
                    // No handle animation for now. We just use AnimatedVisibility to keep Composable "alive".
                    AnimatedVisibility(
                        visible = !isSearchBarExpanded,
                        enter = fadeIn(tween(durationMillis = 0)),
                        exit = fadeOut(tween(durationMillis = 0)),
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier
                                .fillMaxWidth(),
                        )
                    }

                    AnimatedVisibility(
                        visible = isSearchBarExpanded,
                        enter = fadeIn(tween(durationMillis = 0)),
                        exit = fadeOut(tween(durationMillis = 0)),
                    ) {
                        searchBarComposable()
                    }
                },
                backgroundColor = topAppBarBackgroundColor,
                navigationIcon = if (navigationAction != null) {
                    { LbcMenuIconButton(action = navigationAction) }
                } else {
                    null
                },
                actions = {
                    if (((showMenuOnLoading && isLoading) || !isLoading) && !isSearchBarExpanded) {
                        rowActionsComposable?.invoke(this)
                        LbcMenuIconButton(action = searchAction)
                    }
                },
            )
        }
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomStart),
                color = loaderIndicatorColor,
            )
        }
    }
}
