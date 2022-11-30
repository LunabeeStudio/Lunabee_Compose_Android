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

package studio.lunabee.compose.accessibility

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import studio.lunabee.compose.R
import studio.lunabee.compose.lbcaccessibility.extension.setAccessibilityDetails
import studio.lunabee.compose.lbcaccessibility.extension.setAsInvisibleForAccessibility
import studio.lunabee.compose.lbcaccessibility.model.ClickDescription
import studio.lunabee.compose.lbcaccessibility.model.ToggleDescription
import studio.lunabee.compose.lbcaccessibility.state.AccessibilityState
import studio.lunabee.compose.material.common.item.SwitchItem

object AccessibilityItemFactory {
    fun itemAccessibilityState(
        lazyListScope: LazyListScope,
        accessibilityState: AccessibilityState,
    ) {
        lazyListScope.item {
            Text(
                text = stringResource(id = R.string.accessibility_screen_isEnabled, accessibilityState.isAccessibilityEnabled),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            )
            Text(
                text = stringResource(
                    id = R.string.accessibility_screen_isTouchExplorationEnabled,
                    accessibilityState.isTouchExplorationEnabled,
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }
    }

    fun itemSwitchUiNotAccessible(
        lazyListScope: LazyListScope,
        isChecked: Boolean,
        onCheckChange: (newCheckedValue: Boolean) -> Unit,
    ) {
        lazyListScope.item {
            SwitchItem(
                switchText = stringResource(id = R.string.accessibility_screen_make_layout_accessible),
                isCheckedByDefault = isChecked,
                onCheckChange = onCheckChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
                    .setAccessibilityDetails(
                        stateDescription = if (isChecked) {
                            stringResource(id = R.string.accessibility_screen_custom_content_description_state_enabled)
                        } else {
                            stringResource(id = R.string.accessibility_screen_custom_content_description_state_disabled)
                        },
                        toggleDescription = ToggleDescription(
                            value = isChecked,
                            onValueChanged = onCheckChange,
                            role = Role.Switch,
                        ),
                        isHeading = true,
                    ),
            )
        }
    }

    fun itemColumnAccessible(
        lazyListScope: LazyListScope,
        isLayoutAccessible: Boolean,
    ) {
        lazyListScope.item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .then(
                        if (isLayoutAccessible) {
                            Modifier
                                .setAccessibilityDetails(
                                    mergeDescendants = true,
                                )
                        } else {
                            Modifier
                        },
                    ),
            ) {
                Text(text = stringResource(id = R.string.accessibility_screen_simple_text))
                Text(text = stringResource(id = R.string.accessibility_screen_simple_text2))
            }
        }
    }

    fun itemHeading(
        lazyListScope: LazyListScope,
    ) {
        lazyListScope.item {
            Text(
                text = stringResource(id = R.string.accessibility_screen_simple_text_head),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .setAccessibilityDetails(
                        isHeading = true,
                    ),
            )
        }
    }

    fun itemButtonAccessible(
        lazyListScope: LazyListScope,
    ) {
        lazyListScope.item {
            val context: Context = LocalContext.current
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .setAccessibilityDetails(
                        clickDescription = ClickDescription(
                            clickLabel = stringResource(id = R.string.accessibility_screen_custom_button_on_click_description),
                            action = {
                                Toast
                                    .makeText(context, R.string.accessibility_screen_custom_button_clicked, Toast.LENGTH_LONG)
                                    .show()
                                true
                            },
                        ),
                    ),
            ) {
                Text(text = stringResource(id = R.string.accessibility_screen_custom_button_click_me))
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun itemInvisible(
        lazyListScope: LazyListScope,
    ) {
        lazyListScope.item {
            Text(
                text = stringResource(id = R.string.accessibility_screen_simple_text_not_read),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .setAsInvisibleForAccessibility(),
            )
        }
    }

    fun itemLiveRegion(
        lazyListScope: LazyListScope,
        value: Int,
        onValueChange: () -> Unit,
    ) {
        lazyListScope.item {
            Text(
                text = stringResource(id = R.string.accessibility_screen_current_value, value),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .setAccessibilityDetails(
                        liveRegionMode = LiveRegionMode.Assertive,
                        contentDescription = stringResource(id = R.string.accessibility_screen_current_value_now, value),
                        setAsInvisible = true,
                    ),
            )
        }

        lazyListScope.item {
            Button(
                onClick = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                Text(text = stringResource(id = R.string.accessibility_screen_current_value_increment))
            }
        }
    }
}
