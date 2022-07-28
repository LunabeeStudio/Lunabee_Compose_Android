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
 * LbcSearchTopAppBarState.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 7/17/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbctopappbar.material.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

@Stable
class LbcSearchTopAppBarState(
    isSearchBarExpanded: Boolean,
    searchTextFieldValue: TextFieldValue,
) {
    var isSearchBarExpanded: Boolean by mutableStateOf(value = isSearchBarExpanded)
    var searchTextFieldValue: TextFieldValue by mutableStateOf(value = searchTextFieldValue)

    companion object {
        val Saver: Saver<LbcSearchTopAppBarState, Any> = run {
            val isSearchBarExpandedKey = "isSearchBarExpandedKey"
            val searchTextFieldValueKey = "searchTextFieldValueKey"

            mapSaver(
                save = { state ->
                    mapOf(
                        isSearchBarExpandedKey to state.isSearchBarExpanded,
                        searchTextFieldValueKey to with(TextFieldValue.Saver) { save(value = state.searchTextFieldValue) },
                    )
                },
                restore = { restoredMap ->
                    LbcSearchTopAppBarState(
                        isSearchBarExpanded = restoredMap[isSearchBarExpandedKey] as Boolean,
                        searchTextFieldValue = with(TextFieldValue.Saver) {
                            restore(restoredMap[searchTextFieldValueKey] as List<*>)
                        } ?: TextFieldValue(),
                    )
                },
            )
        }
    }
}

@Composable
fun rememberLbcSearchTopAppBarState(
    isSearchBarExpanded: Boolean = false,
    searchTextFieldValue: TextFieldValue = TextFieldValue(),
): LbcSearchTopAppBarState {
    return rememberSaveable(saver = LbcSearchTopAppBarState.Saver) {
        LbcSearchTopAppBarState(
            isSearchBarExpanded = isSearchBarExpanded,
            searchTextFieldValue = searchTextFieldValue,
        )
    }
}
