package com.lunabee.lbextensions

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
fun Instant.truncateTo(unit: DateTimeUnit): Instant {
    return when (unit) {
        is DateTimeUnit.DayBased ->
            Instant.fromEpochMilliseconds(this.toEpochMilliseconds().let { it - it % unit.days.days.inWholeMilliseconds })
        is DateTimeUnit.MonthBased -> {
            val localDateTime = this.toLocalDateTime(TimeZone.UTC)
            val month = (localDateTime.month.ordinal + 1).let { it - it % unit.months }
            LocalDateTime(localDateTime.year, month.coerceAtLeast(1), 1, 0, 0)
                .toInstant(TimeZone.UTC)
        }
        is DateTimeUnit.TimeBased ->
            Instant.fromEpochMilliseconds(this.toEpochMilliseconds().let { it - it % unit.duration.inWholeMilliseconds })
    }
}
