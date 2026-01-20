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

package com.lunabee.lbextensions

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.test.Test
import kotlin.test.assertEquals

class DateTimeExtTest {

    private val testDate = LocalDateTime(2024, 10, 18, 11, 18, 25, 123_456)
        .toInstant(TimeZone.UTC)

    @Test
    fun `truncateTo second test`() {
        val expected = LocalDateTime(2024, 10, 18, 11, 18, 25)
            .toInstant(TimeZone.UTC)
        val actual = testDate.truncateTo(DateTimeUnit.SECOND)
        assertEquals(expected, actual, "Test date = $testDate")
    }

    @Test
    fun `truncateTo hour test`() {
        val expected = LocalDateTime(2024, 10, 18, 11, 0, 0)
            .toInstant(TimeZone.UTC)
        val actual = testDate.truncateTo(DateTimeUnit.HOUR)
        assertEquals(expected, actual, "Test date = $testDate")
    }

    @Test
    fun `truncateTo day test`() {
        val testValues = mapOf(
            LocalDateTime(2024, 10, 18, 11, 18, 25, 123_456)
                .toInstant(TimeZone.UTC) to
                LocalDateTime(2024, 10, 18, 0, 0, 0)
                    .toInstant(TimeZone.UTC),
            LocalDateTime(2024, 10, 18, 0, 0)
                .toInstant(TimeZone.UTC) to
                LocalDateTime(2024, 10, 18, 0, 0, 0)
                    .toInstant(TimeZone.UTC),
            LocalDateTime(2024, 12, 31, 23, 59, 59, 999_999)
                .toInstant(TimeZone.UTC) to
                LocalDateTime(2024, 12, 31, 0, 0, 0)
                    .toInstant(TimeZone.UTC),
        )

        testValues.forEach { (input, expected) ->
            assertEquals(expected, input.truncateTo(DateTimeUnit.DAY), "input = $input")
        }
    }

    @Test
    fun `truncateTo year test`() {
        val expected = LocalDateTime(2024, 1, 1, 0, 0, 0)
            .toInstant(TimeZone.UTC)
        val actual = testDate.truncateTo(DateTimeUnit.YEAR)
        assertEquals(expected, actual, "Test date = $testDate")
    }

    @Test
    fun `truncateTo month test`() {
        val expected = LocalDateTime(2024, 10, 1, 0, 0, 0)
            .toInstant(TimeZone.UTC)
        val actual = testDate.truncateTo(DateTimeUnit.MONTH)
        assertEquals(expected, actual, "Test date = $testDate")
    }
}
