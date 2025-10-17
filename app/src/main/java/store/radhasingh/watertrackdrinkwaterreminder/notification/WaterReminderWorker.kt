package store.radhasingh.watertrackdrinkwaterreminder.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import store.radhasingh.watertrackdrinkwaterreminder.MainActivity
import store.radhasingh.watertrackdrinkwaterreminder.R
import store.radhasingh.watertrackdrinkwaterreminder.data.repository.UserSettingsRepository
import store.radhasingh.watertrackdrinkwaterreminder.utils.PermissionUtils

class WaterReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    private val userSettingsRepository: UserSettingsRepository by inject()

    companion object {
        const val CHANNEL_ID = "water_reminder_channel"
        const val NOTIFICATION_ID = 1
    }

    override suspend fun doWork(): Result {
        return try {
            val settings = userSettingsRepository.getSettings().let { flow ->
                var currentSettings: store.radhasingh.watertrackdrinkwaterreminder.data.model.UserSettings? = null
                flow.collect { currentSettings = it }
                currentSettings ?: store.radhasingh.watertrackdrinkwaterreminder.data.model.UserSettings()
            }

            if (settings.notificationsEnabled) {
                showNotification(settings.soundEnabled, settings.vibrationEnabled)
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun showNotification(soundEnabled: Boolean, vibrationEnabled: Boolean) {
        createNotificationChannel()

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("open_add_glass", true)
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_water_drop)
            .setContentTitle("Time to Hydrate! 💧")
            .setContentText("Don't forget to drink some water to stay healthy!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .apply {
                if (soundEnabled) {
                    setSound(Uri.parse("android.resource://${applicationContext.packageName}/${R.raw.soft_water_reminder}"))
                } else {
                    setSilent(true)
                }

                if (vibrationEnabled) {
                    setVibrate(longArrayOf(0, 300, 200, 300))
                }
            }
            .build()

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        if (PermissionUtils.hasNotificationPermission(applicationContext)) {
            try {
                notificationManager.notify(NOTIFICATION_ID, notification)
            } catch (e: SecurityException) {
                // Permission was revoked at runtime, ignore notification
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Water Reminders"
            val descriptionText = "Notifications to remind you to drink water"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
