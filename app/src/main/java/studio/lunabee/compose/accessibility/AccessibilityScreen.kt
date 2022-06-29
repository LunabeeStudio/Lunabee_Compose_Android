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

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import studio.lunabee.compose.R
import studio.lunabee.compose.extension.topAppBarElevation
import studio.lunabee.compose.lbcaccessibility.composable.AccessibilityCheckBoxRow
import studio.lunabee.compose.lbcaccessibility.extension.addSemantics
import studio.lunabee.compose.lbcaccessibility.model.AccessibilityDescription
import studio.lunabee.compose.lbcaccessibility.model.OnClickDescription
import studio.lunabee.compose.lbcaccessibility.model.StateDescription
import studio.lunabee.compose.lbcaccessibility.state.AccessibilityState
import studio.lunabee.compose.lbcaccessibility.state.rememberAccessibilityState
import studio.lunabee.compose.lbctopappbar.material.LbcNavigationTopAppBar
import studio.lunabee.compose.navigation.ToDirection

@Composable
fun AccessibilityScreen(
    navigateToPreviousScreen: ToDirection,
    openAccessibilitySettings: () -> Unit,
) {
    val lazyListState: LazyListState = rememberLazyListState()
    val accessibilityState: AccessibilityState = rememberAccessibilityState()

    Scaffold(
        topBar = {
            LbcNavigationTopAppBar(
                title = stringResource(id = R.string.accessibility_title),
                navigationIconRes = R.drawable.ic_back,
                elevation = lazyListState.topAppBarElevation,
                onNavigationClicked = navigateToPreviousScreen,
                applyStatusBarPadding = true,
                backgroundColor = MaterialTheme.colors.surface,
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
    ) {
        LazyColumn(
            state = lazyListState,
            contentPadding = it,
        ) {
            item(
                key = "description_fab_text",
            ) {
                val textToDisplay = if (accessibilityState.isAccessibilityEnabled) {
                    stringResource(id = R.string.accessibility_screen_floating_button_explanation_enabled)
                } else {
                    stringResource(id = R.string.accessibility_screen_floating_button_explanation_disabled)
                }

                Text(
                    text = textToDisplay,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                )

                Divider()
            }

            item(
                key = "default_accessibility_item",
            ) {
                Text(
                    text = stringResource(id = R.string.accessibility_screen_simple_text),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                )
                Divider()
            }

            item(
                key = "content_description_accessibility_item",
            ) {
                Text(
                    text = stringResource(id = R.string.accessibility_screen_simple_text_not_read),
                    modifier = Modifier
                        .addSemantics(
                            accessibilityDescription = AccessibilityDescription(
                                contentDescription = stringResource(id = R.string.accessibility_screen_custom_content_description_click),
                            )
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                )
                Divider()
            }

            item(
                key = "heading_accessibility_item",
            ) {
                Text(
                    style = MaterialTheme.typography.h5,
                    text = stringResource(id = R.string.accessibility_screen_simple_text_head),
                    modifier = Modifier
                        .addSemantics(
                            accessibilityDescription = AccessibilityDescription(
                                contentDescription = stringResource(id = R.string.accessibility_screen_custom_content_description_click),
                                isHeading = true,
                            )
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                )
                Divider()
            }

            item(
                key = "checkbox_accessible_item_key"
            ) {
                if (accessibilityState.isAccessibilityEnabled) {
                    AccessibilityCheckBoxRow(
                        initialCheckStateValue = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 48.dp)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        accessibilityDescription = AccessibilityDescription(
                            stateDescription = StateDescription(
                                stateEnabledDescription = stringResource(
                                    id = R.string.accessibility_screen_custom_content_description_state_enabled,
                                ),
                                stateDisabledDescription = stringResource(
                                    id = R.string.accessibility_screen_custom_content_description_state_disabled,
                                ),
                            )
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.accessibility_screen_custom_content_description_checkbox),
                            modifier = Modifier
                                .weight(weight = 1f),
                        )
                    }
                } else {
                    DefaultCheckBox()
                }
            }

            item(
                key = "button_accessible_item_key",
            ) {
                val context = LocalContext.current
                val clickAction = {
                    Toast.makeText(
                        context,
                        context.getString(R.string.accessibility_screen_custom_button_clicked),
                        Toast.LENGTH_LONG,
                    ).show()
                }
                Button(
                    onClick = clickAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp)
                        .addSemantics(
                            accessibilityDescription = AccessibilityDescription(
                                onClickDescription = OnClickDescription(
                                    action = clickAction,
                                    clickLabel = stringResource(id = R.string.accessibility_screen_custom_button_on_click_description),
                                )
                            )
                        )
                ) {
                    Text(
                        text = stringResource(id = R.string.accessibility_screen_custom_button_click_me),
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun DefaultCheckBox() {
    var isChecked: Boolean by rememberSaveable { mutableStateOf(value = false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp)
            .toggleable(
                value = isChecked,
                onValueChange = { isChecked = it },
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            text = stringResource(id = R.string.accessibility_screen_custom_content_description_checkbox),
            modifier = Modifier
                .weight(weight = 1f),
        )
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
        )
    }
}
