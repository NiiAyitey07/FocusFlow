package com.focusflow.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.focusflow.app.R
import com.focusflow.app.ui.splash.SplashActivity

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val kind = intent.getStringExtra(NotificationScheduler.EXTRA_KIND)
            ?: NotificationScheduler.KIND_MORNING

        val (title, body) = when (kind) {
            NotificationScheduler.KIND_EVENING ->
                "Evening check-in" to "Quick one: did you close your 1–3 focus tasks today?"
            else ->
                "Plan your FocusFlow" to "Pick your 1–3 tasks for today and keep it tight."
        }

        val channelId = "focusflow_reminders"
        ensureChannel(context, channelId)

        val launchIntent = Intent(context, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
        } else {
            android.app.PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent = android.app.PendingIntent.getActivity(context, 0, launchIntent, pendingFlags)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(if (kind == NotificationScheduler.KIND_EVENING) 2002 else 2001, notification)

        // Re-schedule next day (since setExact only schedules once)
        NotificationScheduler.scheduleDailyReminders(context)
    }

    private fun ensureChannel(context: Context, channelId: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val existing = nm.getNotificationChannel(channelId)
        if (existing != null) return

        val channel = NotificationChannel(
            channelId,
            "FocusFlow Reminders",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Daily reminders to plan and complete your FocusFlow tasks."
        }
        nm.createNotificationChannel(channel)
    }
}
