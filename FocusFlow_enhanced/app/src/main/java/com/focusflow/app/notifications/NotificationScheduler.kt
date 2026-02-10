package com.focusflow.app.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.Calendar

/**
 * Schedules two daily reminders:
 * - Morning planning (default 9:00 AM)
 * - Evening check-in (default 8:00 PM)
 */
object NotificationScheduler {
    private const val REQ_MORNING = 9100
    private const val REQ_EVENING = 9101

    const val EXTRA_KIND = "kind"
    const val KIND_MORNING = "morning"
    const val KIND_EVENING = "evening"

    fun scheduleDailyReminders(context: Context, morningHour: Int = 9, eveningHour: Int = 20) {
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Morning
        scheduleDaily(
            alarm = alarm,
            context = context,
            requestCode = REQ_MORNING,
            hour = morningHour,
            minute = 0,
            kind = KIND_MORNING
        )

        // Evening
        scheduleDaily(
            alarm = alarm,
            context = context,
            requestCode = REQ_EVENING,
            hour = eveningHour,
            minute = 0,
            kind = KIND_EVENING
        )
    }

    fun cancelReminders(context: Context) {
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.cancel(buildPendingIntent(context, REQ_MORNING, KIND_MORNING))
        alarm.cancel(buildPendingIntent(context, REQ_EVENING, KIND_EVENING))
    }

    private fun scheduleDaily(
        alarm: AlarmManager,
        context: Context,
        requestCode: Int,
        hour: Int,
        minute: Int,
        kind: String
    ) {
        val cal = Calendar.getInstance().apply {
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        // If time already passed today, schedule for tomorrow.
        if (cal.timeInMillis <= System.currentTimeMillis()) {
            cal.add(Calendar.DAY_OF_YEAR, 1)
        }

        val pi = buildPendingIntent(context, requestCode, kind)

        // Cancel then reschedule (idempotent).
        alarm.cancel(pi)

        // Exact is nicer for reminders, but may be restricted on some devices.
        // We fall back to setExact if possible.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pi)
        } else {
            alarm.setExact(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pi)
        }
    }

    private fun buildPendingIntent(context: Context, requestCode: Int, kind: String): PendingIntent {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(EXTRA_KIND, kind)
        }
        val flags = (PendingIntent.FLAG_UPDATE_CURRENT) or
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        return PendingIntent.getBroadcast(context, requestCode, intent, flags)
    }
}
