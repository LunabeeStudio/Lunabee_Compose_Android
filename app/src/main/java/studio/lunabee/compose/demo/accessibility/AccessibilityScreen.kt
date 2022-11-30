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
 * AccessibilityScreen.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 11/30/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.accessibility

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import studio.lunabee.compose.accessibility.state.AccessibilityState
import studio.lunabee.compose.accessibility.state.rememberAccessibilityState

@Composable
fun AccessibilityScreen() {
    val lazyListState: LazyListState = rememberLazyListState()
    val accessibilityState: AccessibilityState = rememberAccessibilityState()
    var currentValue: Int by rememberSaveable { mutableStateOf(value = 0) }

    LazyColumn(
        state = lazyListState,
    ) {
        AccessibilityItemFactory.itemAccessibilityState(
            lazyListScope = this,
            accessibilityState = accessibilityState,
        )

        AccessibilityItemFactory.itemColumnAccessible(
            lazyListScope = this,
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

        AccessibilityItemFactory.itemCustomString(
            lazyListScope = this,
        )
    }
}
