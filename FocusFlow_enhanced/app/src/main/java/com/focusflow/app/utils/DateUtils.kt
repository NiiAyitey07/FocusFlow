package com.focusflow.app.utils

import java.util.Calendar
import java.util.Date

/**
 * Date helpers used throughout the app.
 *
 * FocusFlow stores "task date" as a Date in Room. To make equality queries reliable,
 * we always normalise to the start of the day (00:00:00.000) in the device's timezone.
 */
object DateUtils {

    fun startOfDay(date: Date = Date()): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.time
    }

    fun daysBetweenInclusive(start: Date, end: Date): Int {
        val s = startOfDay(start).time
        val e = startOfDay(end).time
        val diffMs = e - s
        val oneDay = 24L * 60L * 60L * 1000L
        return (diffMs / oneDay).toInt() + 1
    }
}
