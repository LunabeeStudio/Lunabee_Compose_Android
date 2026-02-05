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

package studio.lunabee.extension

import kotlinx.datetime.DateTimeUnit
import kotlin.time.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.days

/**
 * Truncate the Instant to the given unit. Not that:
 *   • An intermediate [LocalDateTime] is used for month and year truncation
 *   • Truncating to [DateTimeUnit.WEEK] does not use a calendar so it only truncates on a 7 days based, which generally does not make
 *   sense
 * Inspired by https://slack-chats.kotlinlang.org/t/11976588/hey-everyone-is-there-equivalent-of-truncatedto-chronounit-s#2dff6f6a-222d-4d91-b443-80dd3630554f
 */
fun Instant.truncateTo(unit: DateTimeUnit): Instant = when (unit) {
    is DateTimeUnit.DayBased ->
        Instant
            .fromEpochMilliseconds(this.toEpochMilliseconds().let { it - it % unit.days.days.inWholeMilliseconds })

    is DateTimeUnit.MonthBased -> {
        val localDateTime = this.toLocalDateTime(TimeZone.UTC)
        val month = (localDateTime.month.ordinal + 1).let { it - it % unit.months }
        LocalDateTime(localDateTime.year, month.coerceAtLeast(1), 1, 0, 0)
            .toInstant(TimeZone.UTC)
    }

    is DateTimeUnit.TimeBased ->
        Instant
            .fromEpochMilliseconds(this.toEpochMilliseconds().let { it - it % unit.duration.inWholeMilliseconds })
}
