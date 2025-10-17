package store.radhasingh.watertrackdrinkwaterreminder.notification

import android.content.Context
import androidx.work.*
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import store.radhasingh.watertrackdrinkwaterreminder.data.repository.UserSettingsRepository
import java.util.concurrent.TimeUnit

class WaterReminderNotificationManager(
    private val context: Context
) : KoinComponent {

    private val userSettingsRepository: UserSettingsRepository by inject()
    private val workManager = WorkManager.getInstance(context)

    companion object {
        private const val WORK_NAME = "water_reminder_work"
    }

    suspend fun scheduleReminders() {
        val settings = userSettingsRepository.getSettings().first()
            ?: return

        if (!settings.notificationsEnabled) {
            cancelReminders()
            return
        }

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<WaterReminderWorker>(
            settings.reminderIntervalMinutes.toLong(),
            TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    fun cancelReminders() {
        workManager.cancelUniqueWork(WORK_NAME)
    }

    suspend fun updateReminderSchedule() {
        scheduleReminders()
    }

    suspend fun showPreviewNotification(soundEnabled: Boolean, vibrationEnabled: Boolean) {
        val settings = userSettingsRepository.getSettings().first()
            ?: return

        // Create a preview notification using the same logic as the worker
        val notificationManager = androidx.core.app.NotificationManagerCompat.from(context)
        
        // Create notification channel if needed
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = android.app.NotificationChannel(
                "water_reminder_channel",
                "Water Reminder",
                android.app.NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for water drinking reminders"
                enableVibration(vibrationEnabled)
                if (soundEnabled) {
                    setSound(
                        android.net.Uri.parse("android.resource://${context.packageName}/store.radhasingh.watertrackdrinkwaterreminder.R.raw.soft_water_reminder"),
                        null
                    )
                } else {
                    setSound(null, null)
                }
            }
            val systemNotificationManager = context.getSystemService(android.content.Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            systemNotificationManager.createNotificationChannel(channel)
        }

        val intent = android.content.Intent(context, store.radhasingh.watertrackdrinkwaterreminder.MainActivity::class.java).apply {
            flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("open_add_glass", true)
        }

        val pendingIntent = android.app.PendingIntent.getActivity(
            context,
            999, // Use different ID for preview
            intent,
            android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
        )

        val notification = androidx.core.app.NotificationCompat.Builder(context, "water_reminder_channel")
            .setSmallIcon(store.radhasingh.watertrackdrinkwaterreminder.R.drawable.ic_water_drop)
            .setContentTitle("Time to Hydrate! 💧")
            .setContentText("Don't forget to drink some water to stay healthy!")
            .setPriority(androidx.core.app.NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .apply {
                if (soundEnabled) {
                    setSound(android.net.Uri.parse("android.resource://${context.packageName}/store.radhasingh.watertrackdrinkwaterreminder.R.raw.soft_water_reminder"))
                } else {
                    setSilent(true)
                }

                if (vibrationEnabled) {
                    setVibrate(longArrayOf(0, 300, 200, 300))
                }
            }
            .build()

        notificationManager.notify(999, notification) // Use different ID for preview
    }
}
