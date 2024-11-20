/*
 * Copyright (c) 2024 Lunabee Studio
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
 * LbcTextSpecTest.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 11/20/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.core.kmp

import studio.lunabee.compose.core.cmp.ext.format
import kotlin.test.Test
import kotlin.test.assertEquals

class LbcTextSpecTest {
    @Test
    fun givenStringToFormat_whenFormatingStringWithArgs_thenStringParamsShouldBeReplacedWithArgs() {
        val stringToFormat = "I need to format %d %s strings!"
        val expectedFormatedString = "I need to format 10 huge strings!"

        val actualFormatedString = stringToFormat.format(10, "huge")

        assertEquals(
            expected = expectedFormatedString,
            actual = actualFormatedString,
            message = "The string should be correctly formated."
        )
    }

    @Test
    fun givenStringWithComplexParamsToFormat_whenFormatingStringWithArgs_thenStringParamsShouldBeReplacedWithArgs() {
        val stringToFormat = "I need to format %1\$d %2\$s strings!"
        val expectedFormatedString = "I need to format 10 huge strings!"

        val actualFormatedString = stringToFormat.format(10, "huge")

        assertEquals(
            expected = expectedFormatedString,
            actual = actualFormatedString,
            message = "The string should be correctly formated."
        )
    }

    @Test
    fun givenStringWithParamsToFormat_whenFormatingStringWithMissingArgs_thenParamsShouldNotBeReplaced() {
        val stringToFormat = "I need to format %d %s strings!"
        val expectedFormatedString = "I need to format 10 %s strings!"

        val actualFormatedString = stringToFormat.format(10)

        assertEquals(
            expected = expectedFormatedString,
            actual = actualFormatedString,
            message = "The string should be correctly formated."
        )
    }
}