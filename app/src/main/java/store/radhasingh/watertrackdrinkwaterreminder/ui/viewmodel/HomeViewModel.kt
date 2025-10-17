package store.radhasingh.watertrackdrinkwaterreminder.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import store.radhasingh.watertrackdrinkwaterreminder.data.model.DrinkEntry
import store.radhasingh.watertrackdrinkwaterreminder.data.model.UserSettings
import store.radhasingh.watertrackdrinkwaterreminder.data.repository.DrinkEntryRepository
import store.radhasingh.watertrackdrinkwaterreminder.data.repository.UserSettingsRepository
import store.radhasingh.watertrackdrinkwaterreminder.audio.SoundManager
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
    
    private var soundManager: SoundManager? = null

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

    fun initializeSoundManager(context: Context) {
        soundManager = SoundManager(context)
    }
    
    fun addDrinkEntry(drinkType: String, volume: Int) {
        viewModelScope.launch {
            val entry = DrinkEntry(
                drinkType = drinkType,
                volume = volume,
                timestamp = LocalDateTime.now()
            )
            drinkEntryRepository.insertEntry(entry)
            
            // Play add glass sound
            val settings = _userSettings.value
            if (settings != null) {
                soundManager?.playAddGlassSound(settings)
                
                // Check if goal is achieved and play appropriate sound
                val newTotal = _todayTotalVolume.value + volume
                val goal = settings.dailyGoal
                if (newTotal >= goal && _todayTotalVolume.value < goal) {
                    // Goal just achieved
                    if (settings.voiceEnabled) {
                        soundManager?.playGoalCompleteVoice(settings)
                    } else {
                        soundManager?.playGoalAchievedSound(settings)
                    }
                }
            }
        }
    }

    fun deleteDrinkEntry(entry: DrinkEntry) {
        viewModelScope.launch {
            drinkEntryRepository.deleteEntry(entry)
        }
    }
    
    fun playButtonTapSound() {
        val settings = _userSettings.value
        if (settings != null) {
            soundManager?.playButtonTapSound(settings)
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        soundManager?.release()
    }
}
