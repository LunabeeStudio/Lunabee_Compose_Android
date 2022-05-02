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
 * TopAppBarScreen.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.material.topappbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import studio.lunabee.compose.R
import studio.lunabee.compose.extension.topAppBarElevation
import studio.lunabee.compose.lbctopappbar.material.LbcLoadingNavigationTopAppBar
import studio.lunabee.compose.lbctopappbar.material.LbcLoadingTopAppBar
import studio.lunabee.compose.lbctopappbar.material.LbcNavigationTopAppBar
import studio.lunabee.compose.lbctopappbar.material.LbcTopAppBar
import studio.lunabee.compose.material.common.item.SwitchItem
import studio.lunabee.compose.navigation.ToDirection

/**
 * Display composable of [studio.lunabee.compose.lbctopappbar] module.
 *
 * @param navigateToPreviousScreen action on navigation clicked
 */
@Composable
fun TopAppBarScreen(
    navigateToPreviousScreen: ToDirection,
) {
    val lazyListState: LazyListState = rememberLazyListState()

    Scaffold(
        topBar = {
            LbcNavigationTopAppBar(
                title = stringResource(id = R.string.top_bar_list_title),
                navigationIconRes = R.drawable.ic_back,
                elevation = lazyListState.topAppBarElevation,
                onNavigationClicked = { navigateToPreviousScreen() },
                applyStatusBarPadding = true,
                backgroundColor = MaterialTheme.colors.surface,
            )
        }
    ) {
        var isElevationEnabled: Boolean by rememberSaveable { mutableStateOf(value = false) }
        val currentElevation = if (isElevationEnabled) AppBarDefaults.TopAppBarElevation else 0.dp
        var isStatusBarPaddingEnabled: Boolean by rememberSaveable { mutableStateOf(value = false) }
        var isLoading: Boolean by rememberSaveable { mutableStateOf(value = true) }
        var isMenuShowingWhenLoading: Boolean by rememberSaveable { mutableStateOf(value = true) }

        LazyColumn(
            state = lazyListState,
        ) {
            item(
                key = R.string.top_bar_screen_description,
            ) {
                Text(
                    modifier = Modifier
                        .padding(all = 16.dp),
                    text = stringResource(id = R.string.top_bar_screen_description),
                )

                Divider()
            }

            item(
                key = R.string.top_bar_screen_elevate_top_bar_action,
            ) {
                SwitchItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isElevationEnabled = !isElevationEnabled }
                        .padding(horizontal = 16.dp),
                    switchText = stringResource(id = R.string.top_bar_screen_elevate_top_bar_action),
                    isCheckedByDefault = isElevationEnabled,
                    onCheckChange = { isElevationEnabled = it },
                )

                Divider()
            }

            item(
                key = R.string.top_bar_screen_elevate_status_bar_padding_action,
            ) {
                SwitchItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isStatusBarPaddingEnabled = !isStatusBarPaddingEnabled }
                        .padding(horizontal = 16.dp),
                    switchText =
                    stringResource(id = R.string.top_bar_screen_elevate_status_bar_padding_action),
                    isCheckedByDefault = isStatusBarPaddingEnabled,
                    onCheckChange = { isStatusBarPaddingEnabled = it },
                )

                Divider()
            }

            item(
                key = R.string.top_bar_screen_is_loading_action,
            ) {
                SwitchItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isLoading = !isLoading }
                        .padding(horizontal = 16.dp),
                    switchText =
                    stringResource(id = R.string.top_bar_screen_is_loading_action),
                    isCheckedByDefault = isLoading,
                    onCheckChange = { isLoading = it },
                )

                Divider()
            }

            item(
                key = R.string.top_bar_screen_is_menu_showing_action,
            ) {
                SwitchItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isMenuShowingWhenLoading = !isMenuShowingWhenLoading }
                        .padding(horizontal = 16.dp),
                    switchText =
                    stringResource(id = R.string.top_bar_screen_is_menu_showing_action),
                    isCheckedByDefault = isMenuShowingWhenLoading,
                    onCheckChange = { isMenuShowingWhenLoading = it },
                )

                Divider()
            }

            item(
                key = R.string.top_bar_screen_simple_description,
            ) {
                TopAppBarDisplayableItem(
                    title = stringResource(
                        id = R.string.top_bar_screen_simple_description,
                    ),
                ) {
                    LbcTopAppBar(
                        title = "TopAppBar",
                        elevation = currentElevation,
                        applyStatusBarPadding = isStatusBarPaddingEnabled,
                    )
                }
            }

            item(
                key = R.string.top_bar_screen_back_description,
            ) {
                TopAppBarDisplayableItem(
                    title = stringResource(id = R.string.top_bar_screen_back_description),
                ) {
                    LbcNavigationTopAppBar(
                        navigationIconRes = R.drawable.ic_back,
                        onNavigationClicked = { },
                        title = "TopAppBar",
                        elevation = currentElevation,
                        applyStatusBarPadding = isStatusBarPaddingEnabled,
                    )
                }
            }

            item(
                key = R.string.top_bar_screen_simple_menu_description,
            ) {
                TopAppBarDisplayableItem(
                    title = stringResource(id = R.string.top_bar_screen_simple_menu_description),
                ) {
                    LbcTopAppBar(
                        title = "TopAppBar",
                        elevation = currentElevation,
                        applyStatusBarPadding = isStatusBarPaddingEnabled,
                        rowActionsComposable = { RowActionMenu() },
                    )
                }
            }

            item(
                key = R.string.top_bar_screen_back_menu_description,
            ) {
                TopAppBarDisplayableItem(
                    title = stringResource(id = R.string.top_bar_screen_back_menu_description),
                ) {
                    LbcNavigationTopAppBar(
                        navigationIconRes = R.drawable.ic_back,
                        onNavigationClicked = { },
                        title = "TopAppBar",
                        elevation = currentElevation,
                        applyStatusBarPadding = isStatusBarPaddingEnabled,
                        rowActionsComposable = { RowActionMenu() },
                    )
                }
            }

            item(
                key = R.string.top_bar_screen_custom_title_description,
            ) {
                TopAppBarDisplayableItem(
                    title = stringResource(id = R.string.top_bar_screen_custom_title_description),
                ) {
                    LbcTopAppBar(
                        elevation = currentElevation,
                        applyStatusBarPadding = isStatusBarPaddingEnabled,
                        titleComposable = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_logo),
                                contentDescription = null,
                            )
                        }
                    )
                }
            }

            item(
                key = R.string.top_bar_screen_loading_description,
            ) {
                TopAppBarDisplayableItem(
                    title = stringResource(id = R.string.top_bar_screen_loading_description),
                ) {
                    LbcLoadingTopAppBar(
                        title = "TopAppBar",
                        isLoading = isLoading,
                        showMenuOnLoading = isMenuShowingWhenLoading,
                        elevation = currentElevation,
                        applyStatusBarPadding = isStatusBarPaddingEnabled,
                        loaderIndicatorColor = Color.DarkGray,
                        rowActionsComposable = { RowActionMenu() },
                    )
                }
            }

            item(
                key = R.string.top_bar_screen_loading_navigation_description,
            ) {
                TopAppBarDisplayableItem(
                    title = stringResource(id = R.string.top_bar_screen_loading_navigation_description),
                ) {
                    LbcLoadingNavigationTopAppBar(
                        title = "TopAppBar",
                        isLoading = isLoading,
                        showMenuOnLoading = isMenuShowingWhenLoading,
                        elevation = currentElevation,
                        applyStatusBarPadding = isStatusBarPaddingEnabled,
                        loaderIndicatorColor = Color.DarkGray,
                        navigationIconRes = R.drawable.ic_back,
                        rowActionsComposable = { RowActionMenu() },
                        onNavigationClicked = { },
                    )
                }
            }

            // Add additional elements before end space.

            item(key = "endSpace") {
                Spacer(
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                )
            }
        }
    }
}

@Composable
private fun TopAppBarDisplayableItem(
    title: String,
    topAppBarComposable: @Composable () -> Unit,
) {
    Column {
        Text(
            modifier = Modifier
                .padding(all = 16.dp),
            text = title,
            style = MaterialTheme.typography.subtitle2,
        )

        topAppBarComposable()
    }
}

@Composable
private fun RowActionMenu() {
    IconButton(
        onClick = { },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_menu_add),
            contentDescription = null,
        )
    }

    IconButton(
        onClick = { },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_menu_delete),
            contentDescription = null,
        )
    }
}
