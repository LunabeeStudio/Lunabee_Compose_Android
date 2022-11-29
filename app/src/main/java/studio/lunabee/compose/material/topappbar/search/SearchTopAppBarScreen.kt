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

package studio.lunabee.compose.material.topappbar.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import studio.lunabee.compose.R
import studio.lunabee.compose.extension.topAppBarElevation
import studio.lunabee.compose.lbctopappbar.material.LbcTopAppBar
import studio.lunabee.compose.lbctopappbar.material.model.LbcTopAppBarAction
import studio.lunabee.compose.lbctopappbar.material.state.LbcSearchTopAppBarState
import studio.lunabee.compose.lbctopappbar.material.state.rememberLbcSearchTopAppBarState
import studio.lunabee.compose.material.topappbar.TopAppBarItemFactory
import studio.lunabee.compose.material.topappbar.TopAppBarOption
import studio.lunabee.compose.material.topappbar.state.TopAppBarState
import studio.lunabee.compose.material.topappbar.state.rememberTopAppBarState
import studio.lunabee.compose.navigation.ToDirection

@Composable
fun SearchTopAppBarScreen(
    navigateToPreviousScreen: ToDirection,
) {
    val lazyListState: LazyListState = rememberLazyListState()
    val topAppBarState: TopAppBarState = rememberTopAppBarState()
    val searchTopAppBarState: LbcSearchTopAppBarState = rememberLbcSearchTopAppBarState()
    val focusRequester: FocusRequester = remember { FocusRequester() }

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
        },
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
                        TopAppBarOption.Loading,
                        TopAppBarOption.ShowMenuWhenLoading,
                    ),
                    topAppBarState = topAppBarState,
                    lazyListScope = this,
                )

                TopAppBarItemFactory.showSearchTopAppBar(
                    searchTopAppBarState = searchTopAppBarState,
                    titleRes = R.string.top_bar_screen_simple_description,
                    topAppBarState = topAppBarState,
                    lazyListScope = this,
                )

                TopAppBarItemFactory.showSearchTopAppBarWithMenu(
                    searchTopAppBarState = searchTopAppBarState,
                    titleRes = R.string.top_bar_screen_simple_menu_description,
                    topAppBarState = topAppBarState,
                    focusRequester = focusRequester,
                    lazyListScope = this,
                )
            }
        }

        LaunchedEffect(key1 = searchTopAppBarState.isSearchBarExpanded) {
            if (searchTopAppBarState.isSearchBarExpanded) {
                focusRequester.requestFocus()
            }
        }

        BackHandler {
            if (searchTopAppBarState.isSearchBarExpanded) {
                searchTopAppBarState.searchTextFieldValue = TextFieldValue()
                searchTopAppBarState.isSearchBarExpanded = false
            } else {
                navigateToPreviousScreen()
            }
        }
    }
}
