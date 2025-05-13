/*
 * Copyright (c) 2025 Lunabee Studio
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
 * CountryCodeSearchItem.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/7/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.countrypicker

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import studio.lunabee.compose.core.LbcImageSpec
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.countrypicker.ext.normalized

/**
 * Represents a country search item.
 *
 * @property name The name of the country.
 * @property countryCode The country phone code (e.g., "33", "1", "225").
 * @property flag The flag of the country as an [LbcImageSpec].
 * @property isSelected Indicates if the country is currently selected.
 */
data class CountrySearchItem(
    val name: String,
    val countryPhoneCode: String,
    val flag: LbcImageSpec,
    val isSelected: Boolean,
    val isoName: String,
) {

    /**
     * Returns an [LbcTextSpec.Annotated] representation of the country's name, with parts of the name
     * matching the [searchedText] highlighted using the provided [matchingSpanStyle].
     *
     * The search is case-insensitive and uses Unicode normalization (NFD form) for accurate matching.
     *
     * @param searchedText The text to search for within the country's name.
     * @param nonMatchingSpanStyle The [SpanStyle] to apply to parts of the name that do not match the [searchedText].
     * @param matchingSpanStyle The [SpanStyle] to apply to parts of the name that match the [searchedText].
     * @return An [LbcTextSpec.Annotated] containing the formatted country name.
     */
    fun getAnnotatedName(
        searchedText: String,
        nonMatchingSpanStyle: SpanStyle,
        matchingSpanStyle: SpanStyle,
    ): LbcTextSpec.Annotated {
        return LbcTextSpec.Annotated(
            buildAnnotatedString {
                val sanitizedName: String = name.normalized()
                val sanitizedSearch: String = searchedText.normalized()

                if (sanitizedSearch.isNotBlank() && sanitizedName.contains(sanitizedSearch)) {
                    val startIndex: Int = sanitizedName.indexOf(sanitizedSearch)
                    val endIndex: Int = startIndex + sanitizedSearch.length

                    // Append part before match
                    append(name.substring(0, startIndex))
                    addStyle(nonMatchingSpanStyle, 0, startIndex)

                    // Append matched part
                    append(name.substring(startIndex, endIndex))
                    addStyle(matchingSpanStyle, startIndex, endIndex)

                    // Append the rest
                    append(name.substring(endIndex))
                    addStyle(nonMatchingSpanStyle, endIndex, name.length)
                } else {
                    append(name)
                    addStyle(nonMatchingSpanStyle, 0, name.length)
                }
            },
        )
    }
}
