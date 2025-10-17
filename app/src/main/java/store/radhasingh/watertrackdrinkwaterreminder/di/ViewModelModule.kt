package store.radhasingh.watertrackdrinkwaterreminder.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import store.radhasingh.watertrackdrinkwaterreminder.ui.viewmodel.HomeViewModel
import store.radhasingh.watertrackdrinkwaterreminder.ui.viewmodel.SettingsViewModel

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }
}
