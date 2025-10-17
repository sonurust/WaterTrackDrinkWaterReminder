package store.radhasingh.watertrackdrinkwaterreminder.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import store.radhasingh.watertrackdrinkwaterreminder.notification.WaterReminderNotificationManager

val notificationModule = module {
    single { WaterReminderNotificationManager(androidContext()) }
}
