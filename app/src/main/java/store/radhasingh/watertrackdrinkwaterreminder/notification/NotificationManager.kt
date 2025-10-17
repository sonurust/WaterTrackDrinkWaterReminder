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
}
