/*
 * Copyright (c) 2026 Lunabee Studio
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
 */

package studio.lunabee.compose.foundation.uifield.countrypicker

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.field.style.UiFieldStyleData

/**
 * Interface to provide a custom rendering of the country code picker bottom sheet.
 * This allows to customize the bottom sheet, the search field, and the country row.
 */
interface CountryPickerBottomSheetRenderer {

    /**
     * Holder for the bottom sheet content.
     *
     * @param dismiss Callback invoked when the bottom sheet should be dismissed.
     * @param searchField Composable function representing the search field.
     * @param countriesList Composable function representing the list of countries.
     *   It provides the [contentPadding] and [lazyListState] for the list.
     */
    @Composable
    fun BottomSheetHolder(
        dismiss: () -> Unit,
        searchField: @Composable () -> Unit,
        countriesList: @Composable (contentPadding: PaddingValues, lazyListState: LazyListState) -> Unit,
    )

    /**
     * Render a row for a country in the country picker bottom sheet.
     *
     * @param countrySearchItem The [CountrySearchItem] to display.
     * @param searchedText The text entered in the search field.
     * @param onClick The action to perform when the country row is clicked.
     */
    @Composable
    fun CountryRow(
        countrySearchItem: CountrySearchItem,
        searchedText: String,
        onClick: () -> Unit,
    )

    /**
     * The label of search field.
     */
    val searchedFieldLabel: LbcTextSpec?

    /**
     * The placeholder to display in the search field.
     */
    val searchFieldPlaceHolder: LbcTextSpec?

    /**
     * Data to customize the style of the search field.
     */
    val searchFieldStyleData: UiFieldStyleData
}
