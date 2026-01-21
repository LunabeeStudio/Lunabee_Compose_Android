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

@file:Suppress("unused")
@file:JvmName("DateUtils")

package studio.lunabee.extension

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

private val shortFormat
    get() = SimpleDateFormat.getDateInstance(DateFormat.SHORT)
private val mediumFormat
    get() = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM)
private val longFormat
    get() = SimpleDateFormat.getDateInstance(DateFormat.LONG)
private val fullFormat
    get() = SimpleDateFormat.getDateInstance(DateFormat.FULL)
private val nowDate
    get() = Date()

@Deprecated(
    "The name of this function doesn't correspond to what it's return",
    ReplaceWith("shortFormat", "lunabee.studio.lbextensions.shortFormat"),
    DeprecationLevel.ERROR,
)
fun Date.hoursFormat(): String = shortFormat.format(this)

/**
 * Return the [Date] formatted in short form like "11/12/2012"
 */
fun Date.shortFormat(): String = shortFormat.format(this)

/**
 * Return the [Date] formatted in medium form like "11 déc. 2012"
 */
fun Date.mediumFormat(): String = mediumFormat.format(this)

/**
 * Return the [Date] formatted in long form like "11 décembre 2012"
 */
fun Date.longFormat(): String = longFormat.format(this)

/**
 * Return the [Date] formatted in full form like "mardi 11 décembre 2012"
 */
fun Date.fullFormat(): String = fullFormat.format(this)

/**
 * Create a new calendar instance initialized to the date
 *
 * @return new calendar instance
 */
fun Date.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
}

/**
 * Retrieve a ZonedDateTime from a date.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun Date.toZonedDate(): ZonedDateTime {
    return toInstant().atZone(ZoneId.systemDefault())
}

@Deprecated(
    "Use shortFormat instead",
    ReplaceWith("shortFormat()", "lunabee.studio.lbextensions.shortFormat"),
    DeprecationLevel.ERROR,
)
fun Date.dividerFormat(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return String.format(
        Locale.ROOT,
        "%02d/%02d/%02d",
        calendar.get(Calendar.DATE),
        calendar.getMonth() + 1,
        calendar.getYear(),
    )
}

@Deprecated(
    "Use yearsSince instead",
    ReplaceWith("yearsSince(originDate)"),
    DeprecationLevel.WARNING,
)
fun Date.yearsCountSinceDate(originDate: Date): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val originLocaleDate = LocalDate.from(originDate.toInstant())
        val receiverLocaleDate = LocalDate.from(this.toInstant())
        ChronoUnit.YEARS.between(originLocaleDate, receiverLocaleDate).toInt()
    } else {
        val a = toCalendar()
        val b = originDate.toCalendar()
        var diff = a.getYear() - b.getYear()
        if (b.getMonth() > a.getMonth() || a.getMonth() == b.getMonth() && b.get(Calendar.DATE) > a.get(Calendar.DATE)) {
            diff--
        }
        diff
    }
}

/**
 * Return the number of years from [originDate] to the receiver Date.
 */
fun Date.yearsSince(originDate: Date): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val originLocaleDate = originDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val receiverLocaleDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        ChronoUnit.YEARS.between(originLocaleDate, receiverLocaleDate).toInt()
    } else {
        val a = toCalendar()
        val b = originDate.toCalendar()
        var diff = a.getYear() - b.getYear()
        if (b.getMonth() > a.getMonth() || a.getMonth() == b.getMonth() && b.get(Calendar.DATE) > a.get(Calendar.DATE)) {
            diff--
        }
        diff
    }
}

@Deprecated(
    "Use yearsSinceNow instead",
    ReplaceWith("yearsSinceNow()", "lunabee.studio.lbextensions.yearsSinceNow"),
    DeprecationLevel.WARNING,
)
fun Date.yearsCountSinceNow(): Int = yearsSince(nowDate)

/**
 * Return the number of years from now to the receiver Date.
 */
fun Date.yearsSinceNow(): Int = yearsSince(nowDate)

@Deprecated(
    "Use daysSince instead",
    ReplaceWith("daysSince(originDate)", "lunabee.studio.lbextensions.daysSince"),
    DeprecationLevel.WARNING,
)
fun Date.daysCountSinceDate(originDate: Date): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        ChronoUnit.DAYS.between(originDate.toInstant(), this.toInstant()).toInt()
    } else {
        val diff = time - originDate.time
        TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()
    }
}

/**
 * Return the number of days from [originDate] to the receiver Date.
 */
fun Date.daysSince(originDate: Date): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        ChronoUnit.DAYS.between(originDate.toInstant(), this.toInstant()).toInt()
    } else {
        val diff = time - originDate.time
        TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()
    }
}

@Deprecated(
    "Use daysSinceNow instead",
    ReplaceWith("daysSinceNow()", "lunabee.studio.lbextensions.daysSinceNow"),
    DeprecationLevel.WARNING,
)
fun Date.daysCountSinceNow(): Int = daysSince(nowDate)

/**
 * Return the number of days from now to the receiver Date.
 */
fun Date.daysSinceNow(): Int = daysSince(nowDate)

/**
 * Check if the receiver date is earlier in time than [originDate].
 *
 * @receiver The date to check if it is earlier in time.
 * @param originDate The date to check if it is later in time than the receiver.
 * @return true if the receiver is earlier in time than [originDate], false otherwise
 */
fun Date.isEarlierThan(originDate: Date): Boolean = compareCal(originDate) < 0

/**
 * Check if the receiver date is later in time than [originDate].
 *
 * @receiver The date to check if it is later in time.
 * @param originDate The date to check if it is earlier in time than the receiver.
 * @return true if the receiver is later in time than [originDate], false otherwise
 */
fun Date.isLaterThan(originDate: Date): Boolean = compareCal(originDate) > 0

/**
 * Check if the receiver date is the same day as yesterday.
 *
 * @receiver The date to check if it is the same day as yesterday.
 * @return true if the receiver is the same day as yesterday, false otherwise
 */
fun Date.isYesterday(): Boolean = compareCal(nowDate, -1) == 0

/**
 * Check if the receiver date is the same day as now.
 *
 * @receiver The date to check if it is the same day as now.
 * @return true if the receiver is the same day as now, false otherwise
 */
fun Date.isToday(): Boolean = compareCal(nowDate) == 0

/**
 * Check if the receiver date is the same day as tomorrow.
 *
 * @receiver The date to check if it is the same day as tomorrow.
 * @return true if the receiver is the same day as tomorrow, false otherwise
 */
fun Date.isTomorrow(): Boolean = compareCal(nowDate, 1) == 0

/**
 * Check if the receiver date is earlier in time than now.
 *
 * @receiver The date to check if it is earlier in time.
 * @return true if the receiver is earlier in time than now, false otherwise
 */
fun Date.isInThePast(): Boolean = isEarlierThan(nowDate)

/**
 * Check if the receiver date is later in time than now.
 *
 * @receiver The date to check if it is later in time.
 * @return true if the receiver is later in time than now, false otherwise
 */
fun Date.isInTheFutur(): Boolean = isLaterThan(nowDate)

private fun Date.compareCal(originDate: Date, changeDay: Int = 0): Int {
    val originCal = originDate.toCalendar()
    originCal.startOfDay()
    originCal.addDays(changeDay)
    val cal = toCalendar()
    cal.startOfDay()
    return cal.compareTo(originCal)
}
