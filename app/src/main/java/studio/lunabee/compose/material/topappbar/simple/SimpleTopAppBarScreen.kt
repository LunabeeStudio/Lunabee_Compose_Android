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
 * SimpleTopAppBarScreen.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 7/18/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.material.topappbar.simple

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import studio.lunabee.compose.R
import studio.lunabee.compose.extension.topAppBarElevation
import studio.lunabee.compose.lbctopappbar.material.LbcTopAppBar
import studio.lunabee.compose.lbctopappbar.material.model.LbcTopAppBarAction
import studio.lunabee.compose.material.topappbar.TopAppBarItemFactory
import studio.lunabee.compose.material.topappbar.TopAppBarOption
import studio.lunabee.compose.material.topappbar.state.TopAppBarState
import studio.lunabee.compose.material.topappbar.state.rememberTopAppBarState
import studio.lunabee.compose.navigation.ToDirection

@Composable
fun SimpleTopAppBarScreen(
    navigateToPreviousScreen: ToDirection,
) {
    val lazyListState: LazyListState = rememberLazyListState()
    val topAppBarState: TopAppBarState = rememberTopAppBarState()

    Scaffold(
        topBar = {
            LbcTopAppBar(
                title = stringResource(id = R.string.top_bar_list_title),
                elevation = lazyListState.topAppBarElevation,
                navigationAction = LbcTopAppBarAction.DrawableResAction(
                    iconRes = R.drawable.ic_back,
                    contentDescription = null,
                    action = navigateToPreviousScreen,
                ),
                applyStatusBarPadding = true,
                topAppBarBackgroundColor = MaterialTheme.colors.surface,
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(paddingValues = it),
        ) {
            LazyColumn(
                state = lazyListState,
            ) {
                TopAppBarItemFactory.showOptions(
                    options = listOf(
                        TopAppBarOption.Elevation,
                        TopAppBarOption.StatusBarPadding,
                        TopAppBarOption.Navigation,
                    ),
                    topAppBarState = topAppBarState,
                    lazyListScope = this,
                )

                TopAppBarItemFactory.showSimpleTopAppBar(
                    titleRes = R.string.top_bar_screen_simple_description,
                    topAppBarState = topAppBarState,
                    lazyListScope = this,
                )

                TopAppBarItemFactory.showSimpleTopAppBarWithMenu(
                    titleRes = R.string.top_bar_screen_simple_menu_description,
                    topAppBarState = topAppBarState,
                    lazyListScope = this,
                )
            }
        }
    }
}
