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
 * MaterialScreen.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.accessibility

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import studio.lunabee.compose.R
import studio.lunabee.compose.extension.topAppBarElevation
import studio.lunabee.compose.lbcaccessibility.state.AccessibilityState
import studio.lunabee.compose.lbcaccessibility.state.rememberAccessibilityState
import studio.lunabee.compose.lbctopappbar.material.LbcTopAppBar
import studio.lunabee.compose.lbctopappbar.material.model.LbcTopAppBarAction
import studio.lunabee.compose.navigation.ToDirection

@Composable
fun AccessibilityScreen(
    navigateToPreviousScreen: ToDirection,
    openAccessibilitySettings: () -> Unit,
) {
    val lazyListState: LazyListState = rememberLazyListState()
    val accessibilityState: AccessibilityState = rememberAccessibilityState()
    var isLayoutAccessible: Boolean by rememberSaveable { mutableStateOf(value = false) }
    var currentValue: Int by rememberSaveable { mutableStateOf(value = 0) }

    Scaffold(
        topBar = {
            LbcTopAppBar(
                title = stringResource(id = R.string.accessibility_title),
                elevation = lazyListState.topAppBarElevation,
                navigationAction = LbcTopAppBarAction.DrawableResAction(
                    iconRes = R.drawable.ic_back,
                    contentDescription = null,
                    action = navigateToPreviousScreen,
                ),
                applyStatusBarPadding = true,
                topAppBarBackgroundColor = MaterialTheme.colors.surface,
                rowActionsComposable = {
                    if (accessibilityState.isAccessibilityEnabled) {
                        IconButton(onClick = openAccessibilitySettings) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_accessibility),
                                contentDescription = stringResource(id = R.string.accessibility_screen_menu_content_description),
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (!accessibilityState.isAccessibilityEnabled) {
                FloatingActionButton(onClick = openAccessibilitySettings) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_accessibility),
                        tint = MaterialTheme.colors.primary,
                        contentDescription = null, // decorative, button will be moved to TopAppBar if talkback is activate.
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            state = lazyListState,
            contentPadding = paddingValues,
        ) {
            AccessibilityItemFactory.itemAccessibilityState(
                lazyListScope = this,
                accessibilityState = accessibilityState,
            )

            AccessibilityItemFactory.itemSwitchUiNotAccessible(
                lazyListScope = this,
                isChecked = isLayoutAccessible,
                onCheckChange = { isLayoutAccessible = it },
            )

            AccessibilityItemFactory.itemColumnAccessible(
                lazyListScope = this,
                isLayoutAccessible = isLayoutAccessible,
            )

            AccessibilityItemFactory.itemHeading(
                lazyListScope = this,
            )

            AccessibilityItemFactory.itemInvisible(
                lazyListScope = this,
            )

            AccessibilityItemFactory.itemButtonAccessible(
                lazyListScope = this,
            )

            AccessibilityItemFactory.itemLiveRegion(
                lazyListScope = this,
                value = currentValue,
                onValueChange = { currentValue++ },
            )
        }
    }
}
