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
 * TopAppBarItemFactory.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 7/18/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.material.topappbar

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import studio.lunabee.compose.R
import studio.lunabee.compose.lbctopappbar.material.LbcLoadingTopAppBar
import studio.lunabee.compose.lbctopappbar.material.LbcMenuIconButton
import studio.lunabee.compose.lbctopappbar.material.LbcSearchTextField
import studio.lunabee.compose.lbctopappbar.material.LbcSearchTopAppBar
import studio.lunabee.compose.lbctopappbar.material.LbcTopAppBar
import studio.lunabee.compose.lbctopappbar.material.model.LbcTopAppBarAction
import studio.lunabee.compose.lbctopappbar.material.state.LbcSearchTopAppBarState
import studio.lunabee.compose.material.common.item.SwitchItem
import studio.lunabee.compose.material.topappbar.state.TopAppBarState

object TopAppBarItemFactory {
    fun showOptions(
        topAppBarState: TopAppBarState,
        options: List<TopAppBarOption> = TopAppBarOption.values().toList(),
        lazyListScope: LazyListScope,
    ) {
        options.forEach { option ->
            val (isChecked, onClick, onValueChanged) = when (option) {
                TopAppBarOption.Elevation -> {
                    Triple(
                        first = topAppBarState.isElevationEnabled,
                        second = { topAppBarState.isElevationEnabled = !topAppBarState.isElevationEnabled },
                        third = { newCheckedValue: Boolean -> topAppBarState.isElevationEnabled = newCheckedValue },
                    )
                }
                TopAppBarOption.StatusBarPadding -> {
                    Triple(
                        first = topAppBarState.isStatusBarPaddingEnabled,
                        second = { topAppBarState.isStatusBarPaddingEnabled = !topAppBarState.isStatusBarPaddingEnabled },
                        third = { newCheckedValue: Boolean -> topAppBarState.isStatusBarPaddingEnabled = newCheckedValue },
                    )
                }
                TopAppBarOption.Loading -> {
                    Triple(
                        first = topAppBarState.isLoading,
                        second = { topAppBarState.isLoading = !topAppBarState.isLoading },
                        third = { newCheckedValue: Boolean -> topAppBarState.isLoading = newCheckedValue },
                    )
                }
                TopAppBarOption.ShowMenuWhenLoading -> {
                    Triple(
                        first = topAppBarState.isMenuShowingWhenLoading,
                        second = { topAppBarState.isMenuShowingWhenLoading = !topAppBarState.isMenuShowingWhenLoading },
                        third = { newCheckedValue: Boolean -> topAppBarState.isMenuShowingWhenLoading = newCheckedValue },
                    )
                }
                TopAppBarOption.Navigation -> {
                    Triple(
                        first = topAppBarState.isNavigationEnabled,
                        second = { topAppBarState.isNavigationEnabled = !topAppBarState.isNavigationEnabled },
                        third = { newCheckedValue: Boolean -> topAppBarState.isNavigationEnabled = newCheckedValue },
                    )
                }
            }

            lazyListScope.item(
                key = option.name,
            ) {
                SwitchItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onClick)
                        .padding(horizontal = 16.dp),
                    switchText = stringResource(id = option.switchRes),
                    isCheckedByDefault = isChecked,
                    onCheckChange = onValueChanged,
                )
            }
        }
    }

    fun showSimpleTopAppBar(
        @StringRes titleRes: Int,
        topAppBarState: TopAppBarState,
        lazyListScope: LazyListScope,
    ) {
        lazyListScope.item(
            key = titleRes,
        ) {
            Column {
                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                LbcTopAppBar(
                    title = stringResource(id = titleRes),
                    elevation = topAppBarState.currentElevation,
                    applyStatusBarPadding = topAppBarState.isStatusBarPaddingEnabled,
                    navigationAction = LbcTopAppBarAction.DrawableResAction(
                        iconRes = R.drawable.ic_back,
                        contentDescription = null,
                        action = { },
                    ).takeIf { topAppBarState.isNavigationEnabled },
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }

    fun showSimpleTopAppBarWithMenu(
        @StringRes titleRes: Int,
        topAppBarState: TopAppBarState,
        lazyListScope: LazyListScope,
    ) {
        lazyListScope.item(
            key = titleRes,
        ) {
            Column {
                LbcTopAppBar(
                    title = stringResource(id = titleRes),
                    elevation = topAppBarState.currentElevation,
                    applyStatusBarPadding = topAppBarState.isStatusBarPaddingEnabled,
                    navigationAction = LbcTopAppBarAction.DrawableResAction(
                        iconRes = R.drawable.ic_back,
                        contentDescription = null,
                        action = { },
                    ).takeIf { topAppBarState.isNavigationEnabled },
                    rowActionsComposable = {
                        LbcMenuIconButton(
                            action = LbcTopAppBarAction.DrawableResAction(
                                iconRes = R.drawable.ic_menu_add,
                                contentDescription = null,
                                action = { },
                            ),
                        )
                        LbcMenuIconButton(
                            action = LbcTopAppBarAction.DrawableResAction(
                                iconRes = R.drawable.ic_menu_delete,
                                contentDescription = null,
                                action = { },
                            ),
                        )
                    },
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 8.dp))
        }
    }

    fun showLoadingTopAppBar(
        @StringRes titleRes: Int,
        topAppBarState: TopAppBarState,
        lazyListScope: LazyListScope,
    ) {
        lazyListScope.item(
            key = titleRes,
        ) {
            Column {
                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                LbcLoadingTopAppBar(
                    title = stringResource(id = titleRes),
                    elevation = topAppBarState.currentElevation,
                    applyStatusBarPadding = topAppBarState.isStatusBarPaddingEnabled,
                    navigationAction = LbcTopAppBarAction.DrawableResAction(
                        iconRes = R.drawable.ic_back,
                        contentDescription = null,
                        action = { },
                    ).takeIf { topAppBarState.isNavigationEnabled },
                    isLoading = topAppBarState.isLoading,
                    showMenuOnLoading = topAppBarState.isMenuShowingWhenLoading,
                    loaderIndicatorColor = Color.Black,
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }

    fun showLoadingTopAppBarWithMenu(
        @StringRes titleRes: Int,
        topAppBarState: TopAppBarState,
        lazyListScope: LazyListScope,
    ) {
        lazyListScope.item(
            key = titleRes,
        ) {
            Column {
                LbcLoadingTopAppBar(
                    title = stringResource(id = titleRes),
                    elevation = topAppBarState.currentElevation,
                    applyStatusBarPadding = topAppBarState.isStatusBarPaddingEnabled,
                    navigationAction = LbcTopAppBarAction.DrawableResAction(
                        iconRes = R.drawable.ic_back,
                        contentDescription = null,
                        action = { },
                    ).takeIf { topAppBarState.isNavigationEnabled },
                    rowActionsComposable = {
                        LbcMenuIconButton(
                            action = LbcTopAppBarAction.DrawableResAction(
                                iconRes = R.drawable.ic_menu_add,
                                contentDescription = null,
                                action = { },
                            ),
                        )
                        LbcMenuIconButton(
                            action = LbcTopAppBarAction.DrawableResAction(
                                iconRes = R.drawable.ic_menu_delete,
                                contentDescription = null,
                                action = { },
                            ),
                        )
                    },
                    isLoading = topAppBarState.isLoading,
                    showMenuOnLoading = topAppBarState.isMenuShowingWhenLoading,
                    loaderIndicatorColor = Color.Black,
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 8.dp))
        }
    }

    fun showSearchTopAppBar(
        @StringRes titleRes: Int,
        topAppBarState: TopAppBarState,
        searchTopAppBarState: LbcSearchTopAppBarState,
        lazyListScope: LazyListScope,
    ) {
        lazyListScope.item(
            key = titleRes,
        ) {
            val context: Context = LocalContext.current
            Column {
                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                LbcSearchTopAppBar(
                    title = stringResource(id = titleRes),
                    isSearchBarExpanded = searchTopAppBarState.isSearchBarExpanded,
                    elevation = topAppBarState.currentElevation,
                    applyStatusBarPadding = topAppBarState.isStatusBarPaddingEnabled,
                    navigationAction = LbcTopAppBarAction.DrawableResAction(
                        iconRes = R.drawable.ic_back,
                        contentDescription = null,
                        action = {
                            if (searchTopAppBarState.isSearchBarExpanded) {
                                searchTopAppBarState.searchTextFieldValue = TextFieldValue()
                                searchTopAppBarState.isSearchBarExpanded = false
                            } else {
                                Toast.makeText(context, R.string.top_bar_screen_previous_screen, Toast.LENGTH_LONG).show()
                            }
                        },
                    ).takeIf { topAppBarState.isNavigationEnabled },
                    isLoading = topAppBarState.isLoading,
                    showMenuOnLoading = topAppBarState.isMenuShowingWhenLoading,
                    loaderIndicatorColor = Color.Black,
                    searchBarComposable = {
                        LbcSearchTextField(
                            searchTextFieldValue = searchTopAppBarState.searchTextFieldValue,
                            onTextFieldValueChanged = { searchTopAppBarState.searchTextFieldValue = it },
                            clearSearchAction = LbcTopAppBarAction.DrawableResAction(
                                iconRes = R.drawable.ic_clean,
                                contentDescription = null,
                                action = { searchTopAppBarState.searchTextFieldValue = TextFieldValue() },
                            ),
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.top_bar_screen_search_placeholder),
                                    style = MaterialTheme.typography.body2,
                                )
                            }
                        )
                    },
                    searchAction = LbcTopAppBarAction.DrawableResAction(
                        iconRes = R.drawable.ic_search,
                        contentDescription = null,
                        action = { searchTopAppBarState.isSearchBarExpanded = true },
                    ),
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }

    fun showSearchTopAppBarWithMenu(
        @StringRes titleRes: Int,
        topAppBarState: TopAppBarState,
        searchTopAppBarState: LbcSearchTopAppBarState,
        focusRequester: FocusRequester,
        lazyListScope: LazyListScope,
    ) {
        lazyListScope.item(
            key = titleRes,
        ) {
            val context: Context = LocalContext.current
            Column {
                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                LbcSearchTopAppBar(
                    title = stringResource(id = titleRes),
                    isSearchBarExpanded = searchTopAppBarState.isSearchBarExpanded,
                    elevation = topAppBarState.currentElevation,
                    applyStatusBarPadding = topAppBarState.isStatusBarPaddingEnabled,
                    navigationAction = LbcTopAppBarAction.DrawableResAction(
                        iconRes = R.drawable.ic_back,
                        contentDescription = null,
                        action = {
                            if (searchTopAppBarState.isSearchBarExpanded) {
                                searchTopAppBarState.searchTextFieldValue = TextFieldValue()
                                searchTopAppBarState.isSearchBarExpanded = false
                            } else {
                                Toast.makeText(context, R.string.top_bar_screen_previous_screen, Toast.LENGTH_LONG).show()
                            }
                        },
                    ).takeIf { topAppBarState.isNavigationEnabled },
                    isLoading = topAppBarState.isLoading,
                    showMenuOnLoading = topAppBarState.isMenuShowingWhenLoading,
                    loaderIndicatorColor = Color.Black,
                    searchBarComposable = {
                        LbcSearchTextField(
                            searchTextFieldValue = searchTopAppBarState.searchTextFieldValue,
                            onTextFieldValueChanged = { searchTopAppBarState.searchTextFieldValue = it },
                            clearSearchAction = LbcTopAppBarAction.DrawableResAction(
                                iconRes = R.drawable.ic_clean,
                                contentDescription = null,
                                action = { searchTopAppBarState.searchTextFieldValue = TextFieldValue() },
                            ),
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.top_bar_screen_search_placeholder),
                                    style = MaterialTheme.typography.body2,
                                )
                            },
                            focusRequester = focusRequester,
                        )
                    },
                    searchAction = LbcTopAppBarAction.DrawableResAction(
                        iconRes = R.drawable.ic_search,
                        contentDescription = null,
                        action = { searchTopAppBarState.isSearchBarExpanded = true },
                    ),
                    rowActionsComposable = {
                        LbcMenuIconButton(
                            action = LbcTopAppBarAction.DrawableResAction(
                                iconRes = R.drawable.ic_menu_add,
                                contentDescription = null,
                                action = { },
                            ),
                        )
                    },
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}
