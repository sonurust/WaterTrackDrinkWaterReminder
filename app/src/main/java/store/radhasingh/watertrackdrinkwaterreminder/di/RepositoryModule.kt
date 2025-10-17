package store.radhasingh.watertrackdrinkwaterreminder.di

import org.koin.dsl.module
import store.radhasingh.watertrackdrinkwaterreminder.data.repository.DrinkEntryRepository
import store.radhasingh.watertrackdrinkwaterreminder.data.repository.UserSettingsRepository

val repositoryModule = module {
    single { DrinkEntryRepository(get()) }
    single { UserSettingsRepository(get()) }
}
