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

package studio.lunabee.compose.accessibility

import studio.lunabee.compose.accessibility.LbcAccessibilityUtils.cleanForAccessibility
import kotlin.test.Test
import kotlin.test.assertEquals

class LbcAccessibilityUtilsTest {
    @Test
    fun givenStringWithEmojis_whenCleaningStringWithAccessibilityUtils_thenStringShouldBeWithoutEmojis() {
        val stringToClear = "This string needs to be cleaned \uD83D\uDC40"

        val expectedCleanString = "This string needs to be cleaned "

        assertEquals(
            expected = expectedCleanString,
            actual = stringToClear.cleanForAccessibility(),
            message = "This string should be cleared from any emojis",
        )
    }

    @Test
    fun givenStringWithCurrencySymbol_whenCleaningStringWithAccessibilityUtils_thenCurrencySymbolsShouldNotBeenRemoved() {
        val currencyString = "I have 10$ and 50€."

        assertEquals(
            expected = currencyString,
            actual = currencyString.cleanForAccessibility(),
            message = "This string should not have changed",
        )
    }
}
