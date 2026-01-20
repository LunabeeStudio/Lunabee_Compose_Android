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
@file:JvmName("CalendarUtils")

package com.lunabee.lbextensions

import android.content.Context
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Initialize a calendar to the same date as one given in parameter.
 *
 * @param calendar the calendar that will initialize the receiver.
 * @receiver the calendar that will be initialize.
 * @return this for chaining
 */
fun Calendar.initFrom(calendar: Calendar): Calendar {
    time = calendar.time
    return this
}

/**
 * Add a given number of days to the calendar.
 *
 * @param days the number of days to be added.
 * @receiver the calendar that will see it's date increase.
 * @return this for chaining
 */
fun Calendar.addDays(days: Int): Calendar {
    add(Calendar.DATE, days)
    return this
}

/**
 * Remove a given number of days to the calendar.
 *
 * @param days the number of days to be removed.
 * @receiver the calendar that will see it's date decrease.
 * @return this for chaining
 */
fun Calendar.removeDays(days: Int): Calendar {
    addDays(-days)
    return this
}

/**
 * Add a given number of months to the calendar.
 *
 * @param months the number of months to be added.
 * @receiver the calendar that will see it's date increase.
 * @return this for chaining
 */
fun Calendar.addMonths(months: Int): Calendar {
    add(Calendar.MONTH, months)
    return this
}

/**
 * Remove a given number of months to the calendar.
 *
 * @param months the number of days to be removed.
 * @receiver the calendar that will see it's date decrease.
 * @return this for chaining
 */
fun Calendar.removeMonths(months: Int): Calendar {
    addMonths(-months)
    return this
}

/**
 * Add a given number of years to the calendar.
 *
 * @param years the number of years to be added.
 * @receiver the calendar that will see it's date increase.
 * @return this for chaining
 */
fun Calendar.addYears(years: Int): Calendar {
    add(Calendar.YEAR, years)
    return this
}

/**
 * Remove a given number of years to the calendar.
 *
 * @param years the number of years to be removed.
 * @receiver the calendar that will see it's date decrease.
 * @return this for chaining
 */
fun Calendar.removeYears(years: Int): Calendar {
    addYears(-years)
    return this
}

/**
 * Initialize a calendar to the very start of a day which will be **00h 00m 00s 00ms**
 *
 * @receiver The calendar to initialize
 */
fun Calendar.startOfDay() {
    set(Calendar.HOUR, getMinimum(Calendar.HOUR))
    set(Calendar.MINUTE, getMinimum(Calendar.MINUTE))
    set(Calendar.SECOND, getMinimum(Calendar.SECOND))
    set(Calendar.MILLISECOND, getMinimum(Calendar.MILLISECOND))
    set(Calendar.AM_PM, getMinimum(Calendar.AM_PM))
}

/**
 * Initialize a calendar to the very start of a Month which will be **day 01**
 *
 * @receiver The calendar to initialize
 */
fun Calendar.startOfMonth() {
    set(Calendar.DATE, getMinimum(Calendar.DATE))
}

/**
 * Initialize a calendar to the very start of a day which will be **day 31**
 *
 * @receiver The calendar to initialize
 */
fun Calendar.endOfMonth() {
    set(Calendar.DATE, getMaximum(Calendar.DATE))
}

/**
 * Initialize a calendar to the very start of a day which will be **month 0**
 *
 * @receiver The calendar to initialize
 */
fun Calendar.startOfYear() {
    set(Calendar.MONTH, getMinimum(Calendar.MONTH))
}

/**
 * Initialize a calendar to the very start of a day which will be **month 11**
 *
 * @receiver The calendar to initialize
 */
fun Calendar.endOfYear() {
    set(Calendar.MONTH, getMaximum(Calendar.MONTH))
}

/**
 * Check if two calendars have the same day.
 *
 * @param calendarToCompare The first of the two calendar to compare
 * @receiver The second of the two calendar to compare
 * @return true if the 2 calendars have the same day in the same year, false otherwise.
 */
fun Calendar.isSameDayAs(calendarToCompare: Calendar): Boolean {
    return isSameYearAs(calendarToCompare) && get(Calendar.DAY_OF_YEAR) == calendarToCompare.get(
        Calendar.DAY_OF_YEAR,
    )
}

/**
 * Check if two calendars have the same year.
 *
 * @param calendarToCompare The first of the two calendar to compare
 * @receiver The second of the two calendar to compare
 * @return true if the 2 calendars have the same month in the same year, false otherwise.
 */
fun Calendar.isSameMonthAs(calendarToCompare: Calendar): Boolean {
    return isSameYearAs(calendarToCompare) && get(Calendar.MONTH) == calendarToCompare.get(
        Calendar.MONTH,
    )
}

/**
 * Check if two calendars have the same year.
 *
 * @param calendarToCompare The first of the two calendar to compare
 * @receiver The second of the two calendar to compare
 * @return true if the 2 calendars have the same year, false otherwise.
 */
@Suppress("UseExpressionBody")
fun Calendar.isSameYearAs(calendarToCompare: Calendar): Boolean {
    return get(Calendar.YEAR) == calendarToCompare.getYear()
}

/**
 * Check if the current calendar is before the second one in the same month.
 *
 * @param calendarToCompare calendar to be after the receiver
 * @receiver Calendar to be before [calendarToCompare]
 * @return true if the receiver is before [calendarToCompare] in the same month, false otherwise.
 */
fun Calendar.isBeforeInMonthThan(calendarToCompare: Calendar): Boolean = isSameMonthAs(
    calendarToCompare,
) && get(Calendar.DAY_OF_MONTH) < calendarToCompare.get(Calendar.DAY_OF_MONTH)

/**
 * Return the **first** day of the calendar month.
 *
 * @receiver Calendar we want to get the first day of its month
 * @return the **first** day of the calendar month.
 */
fun Calendar.getFirstDayOfMonth(): Int = getActualMinimum(Calendar.DAY_OF_MONTH)

/**
 * Return the **current** day of the calendar month.
 *
 * @receiver Calendar we want to get the current day of its month
 * @return the **current** day of the calendar month.
 */
fun Calendar.getCurrentDayOfMonth(): Int = get(Calendar.DAY_OF_MONTH)

/**
 * Return the **last** day of the calendar month.
 *
 * @receiver Calendar we want to get the last day of its month
 * @return the **last** day of the calendar month.
 */
fun Calendar.getLastDayOfMonth(): Int = getActualMaximum(Calendar.DAY_OF_MONTH)

/**
 * Return the index of the day of the calendar week.
 *
 * From 1 to 7:
 *
 * - 1 => Monday
 * - 2 => Tuesday
 * - 3 => Wednesday
 * - 4 => Thursday
 * - 5 => Friday
 * - 6 => Saturday
 * - 7 => Sunday
 *
 * @receiver Calendar we want to get the current day of its week
 * @return the **current** day of the calendar month.
 */
fun Calendar.getWeekDay(): Int {
    var weekDay = get(Calendar.DAY_OF_WEEK)
    weekDay--
    return if (weekDay == 0) 7 else weekDay
}

/**
 * @receiver Calendar we want to get the current year
 * @return The current year
 */
fun Calendar.getYear(): Int = get(Calendar.YEAR)

/**
 * @receiver Calendar we want to set the current year
 * @return [this] for chaining
 */
fun Calendar.setYear(value: Int): Calendar = apply { set(Calendar.YEAR, value) }

/**
 * @receiver Calendar we want to get the current month
 * @return The current month
 */
fun Calendar.getMonth(): Int = get(Calendar.MONTH)

/**
 * @receiver Calendar we want to set the current month
 * @return [this] for chaining
 */
fun Calendar.setMonth(value: Int): Calendar = apply { set(Calendar.MONTH, value) }

/**
 * Return a string representing the current month name and the year of the calendar.
 *
 * Ex:
 * "mai 2018"
 *
 * @receiver Calendar we want to get the formatted date
 * @return A string representing the **current** month in long character followed by the year.
 */
fun Calendar.getMonthAndYearString(): String = (
    getDisplayName(
        Calendar.MONTH,
        Calendar.LONG,
        Locale.getDefault(),
    ) ?: ""
    ) + " " + get(Calendar.YEAR)

@Deprecated(
    "Use the new method closeDayStringOrFullformat instead",
    ReplaceWith("closeShortFormat(context)", "lunabee.studio.lbextensions.closeShortFormat"),
    DeprecationLevel.ERROR,
)
fun Calendar.closeDayStringOrShortFormat(context: Context): String = closeDayString(context)
    ?: this.time.shortFormat()

@Deprecated(
    "Use the new method closeDayStringOrFullformat instead",
    ReplaceWith("closeMediumFormat(context)", "lunabee.studio.lbextensions.closeMediumFormat"),
    DeprecationLevel.ERROR,
)
fun Calendar.closeDayStringOrMediumFormat(context: Context): String = closeDayString(context)
    ?: this.time.mediumFormat()

@Deprecated(
    "Use the new method closeDayStringOrFullformat instead",
    ReplaceWith("closeLongFormat(context)", "lunabee.studio.lbextensions.closeLongFormat"),
    DeprecationLevel.ERROR,
)
fun Calendar.closeDayStringOrLongFormat(context: Context): String = closeDayString(context)
    ?: this.time.longFormat()

@Deprecated(
    "Use the new method closeDayStringOrFullformat instead",
    ReplaceWith("closeFullformat(context)", "lunabee.studio.lbextensions.closeFullformat"),
    DeprecationLevel.ERROR,
)
fun Calendar.closeDayStringOrFullormat(context: Context): String = closeDayString(context)
    ?: this.time.fullFormat()

@Deprecated(
    "Use the new method closeDayStringOrFullformat instead",
    ReplaceWith("closeFormat(context)", "lunabee.studio.lbextensions.closeFormat"),
    DeprecationLevel.ERROR,
)
fun Calendar.closeDayStringOrFormat(
    context: Context,
    format: SimpleDateFormat,
): String = closeDayString(context) ?: format.format(this.time)

/**
 * Return a string representing the current calendar date by close day or in short format.
 *
 * @receiver Calendar we want to get the formatted date
 * @return A string representing the current calendar date by close day or in short format.
 */
fun Calendar.closeShortFormat(context: Context): String = closeDayString(context)
    ?: this.time.shortFormat()

/**
 * Return a string representing the current calendar date by close day or in medium format.
 *
 * @receiver Calendar we want to get the formatted date
 * @return A string representing the current calendar date by close day or in medium format.
 */
fun Calendar.closeMediumFormat(context: Context): String = closeDayString(context)
    ?: this.time.mediumFormat()

/**
 * Return a string representing the current calendar date by close day or in long format.
 *
 * @receiver Calendar we want to get the formatted date
 * @return A string representing the current calendar date by close day or in long format.
 */
fun Calendar.closeLongFormat(context: Context): String = closeDayString(context)
    ?: this.time.longFormat()

/**
 * Return a string representing the current calendar date by close day or in full format.
 *
 * @receiver Calendar we want to get the formatted date
 * @return A string representing the current calendar date by close day or in full format.
 */
fun Calendar.closeFullformat(context: Context): String = closeDayString(context)
    ?: this.time.fullFormat()

/**
 * Return a string representing the current calendar date by close day or in a specific format.
 *
 * @receiver Calendar we want to get the formatted date
 * @return A string representing the current calendar date by close day or in the provided format.
 */
fun Calendar.closeFormat(
    context: Context,
    format: DateFormat,
): String = closeDayString(context) ?: format.format(this.time)

private fun Calendar.closeDayString(context: Context): String? {
    val calendarToCompare = Calendar.getInstance().removeDays(1)

    return when {
        isSameDayAs(calendarToCompare) -> context.getString(R.string.lbe_common_yesterday)
        isSameDayAs(calendarToCompare.addDays(1)) -> context.getString(R.string.lbe_common_today)
        isSameDayAs(calendarToCompare.addDays(1)) -> context.getString(R.string.lbe_common_tomorrow)
        else -> null
    }
}
