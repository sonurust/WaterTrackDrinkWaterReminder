package store.radhasingh.watertrackdrinkwaterreminder.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import store.radhasingh.watertrackdrinkwaterreminder.data.database.WaterTrackDatabase
import store.radhasingh.watertrackdrinkwaterreminder.data.dao.DrinkEntryDao
import store.radhasingh.watertrackdrinkwaterreminder.data.dao.UserSettingsDao

val databaseModule = module {
    single { WaterTrackDatabase.getDatabase(androidContext()) }
    single<DrinkEntryDao> { get<WaterTrackDatabase>().drinkEntryDao() }
    single<UserSettingsDao> { get<WaterTrackDatabase>().userSettingsDao() }
}
