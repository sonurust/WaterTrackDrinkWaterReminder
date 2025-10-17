package store.radhasingh.watertrackdrinkwaterreminder.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import store.radhasingh.watertrackdrinkwaterreminder.data.model.DrinkEntry
import store.radhasingh.watertrackdrinkwaterreminder.data.model.UserSettings
import store.radhasingh.watertrackdrinkwaterreminder.data.repository.DrinkEntryRepository
import store.radhasingh.watertrackdrinkwaterreminder.data.repository.UserSettingsRepository
import java.time.LocalDate
import java.time.LocalDateTime

class HomeViewModel(
    private val drinkEntryRepository: DrinkEntryRepository,
    private val userSettingsRepository: UserSettingsRepository
) : ViewModel() {

    private val _todayEntries = MutableStateFlow<List<DrinkEntry>>(emptyList())
    val todayEntries: StateFlow<List<DrinkEntry>> = _todayEntries.asStateFlow()

    private val _todayTotalVolume = MutableStateFlow(0)
    val todayTotalVolume: StateFlow<Int> = _todayTotalVolume.asStateFlow()

    private val _userSettings = MutableStateFlow<UserSettings?>(null)
    val userSettings: StateFlow<UserSettings?> = _userSettings.asStateFlow()

    private val _progressPercentage = MutableStateFlow(0f)
    val progressPercentage: StateFlow<Float> = _progressPercentage.asStateFlow()

    init {
        loadTodayData()
        loadUserSettings()
    }

    private fun loadTodayData() {
        viewModelScope.launch {
            val today = LocalDate.now()
            drinkEntryRepository.getEntriesByDate(today).collect { entries ->
                _todayEntries.value = entries
            }
        }

        viewModelScope.launch {
            val today = LocalDate.now()
            drinkEntryRepository.getTotalVolumeByDate(today).collect { total ->
                _todayTotalVolume.value = total ?: 0
                updateProgressPercentage()
            }
        }
    }

    private fun loadUserSettings() {
        viewModelScope.launch {
            userSettingsRepository.getSettings().collect { settings ->
                _userSettings.value = settings
                updateProgressPercentage()
            }
        }
    }

    private fun updateProgressPercentage() {
        val total = _todayTotalVolume.value
        val goal = _userSettings.value?.dailyGoal ?: 2000
        _progressPercentage.value = if (goal > 0) (total.toFloat() / goal).coerceAtMost(1f) else 0f
    }

    fun addDrinkEntry(drinkType: String, volume: Int) {
        viewModelScope.launch {
            val entry = DrinkEntry(
                drinkType = drinkType,
                volume = volume,
                timestamp = LocalDateTime.now()
            )
            drinkEntryRepository.insertEntry(entry)
        }
    }

    fun deleteDrinkEntry(entry: DrinkEntry) {
        viewModelScope.launch {
            drinkEntryRepository.deleteEntry(entry)
        }
    }
}
