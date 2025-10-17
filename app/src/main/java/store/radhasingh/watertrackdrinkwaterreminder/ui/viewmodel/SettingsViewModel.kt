package store.radhasingh.watertrackdrinkwaterreminder.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import store.radhasingh.watertrackdrinkwaterreminder.data.model.UserSettings
import store.radhasingh.watertrackdrinkwaterreminder.data.repository.UserSettingsRepository
import store.radhasingh.watertrackdrinkwaterreminder.notification.WaterReminderNotificationManager

class SettingsViewModel(
    private val userSettingsRepository: UserSettingsRepository
) : ViewModel(), KoinComponent {

    private val notificationManager: WaterReminderNotificationManager by inject()

    private val _userSettings = MutableStateFlow<UserSettings?>(null)
    val userSettings: StateFlow<UserSettings?> = _userSettings.asStateFlow()

    init {
        loadUserSettings()
        initializeNotifications()
    }

    private fun loadUserSettings() {
        viewModelScope.launch {
            userSettingsRepository.getSettings().collect { settings ->
                _userSettings.value = settings ?: UserSettings()
            }
        }
    }

    fun updateDailyGoal(goal: Int) {
        viewModelScope.launch {
            val currentSettings = _userSettings.value ?: UserSettings()
            val updatedSettings = currentSettings.copy(dailyGoal = goal)
            userSettingsRepository.updateSettings(updatedSettings)
        }
    }

    fun updateReminderInterval(intervalMinutes: Int) {
        viewModelScope.launch {
            val currentSettings = _userSettings.value ?: UserSettings()
            val updatedSettings = currentSettings.copy(reminderIntervalMinutes = intervalMinutes)
            userSettingsRepository.updateSettings(updatedSettings)
            notificationManager.updateReminderSchedule()
        }
    }

    fun updateSoundEnabled(enabled: Boolean) {
        viewModelScope.launch {
            val currentSettings = _userSettings.value ?: UserSettings()
            val updatedSettings = currentSettings.copy(soundEnabled = enabled)
            userSettingsRepository.updateSettings(updatedSettings)
        }
    }

    fun updateNotificationSound(soundName: String) {
        viewModelScope.launch {
            val currentSettings = _userSettings.value ?: UserSettings()
            val updatedSettings = currentSettings.copy(selectedNotificationSound = soundName)
            userSettingsRepository.updateSettings(updatedSettings)
        }
    }

    fun updateButtonSound(soundName: String) {
        viewModelScope.launch {
            val currentSettings = _userSettings.value ?: UserSettings()
            val updatedSettings = currentSettings.copy(selectedButtonSound = soundName)
            userSettingsRepository.updateSettings(updatedSettings)
        }
    }

    fun updateGoalSound(soundName: String) {
        viewModelScope.launch {
            val currentSettings = _userSettings.value ?: UserSettings()
            val updatedSettings = currentSettings.copy(selectedGoalSound = soundName)
            userSettingsRepository.updateSettings(updatedSettings)
        }
    }

    fun updateVoiceEnabled(enabled: Boolean) {
        viewModelScope.launch {
            val currentSettings = _userSettings.value ?: UserSettings()
            val updatedSettings = currentSettings.copy(voiceEnabled = enabled)
            userSettingsRepository.updateSettings(updatedSettings)
        }
    }

    fun updateAmbientSoundEnabled(enabled: Boolean) {
        viewModelScope.launch {
            val currentSettings = _userSettings.value ?: UserSettings()
            val updatedSettings = currentSettings.copy(ambientSoundEnabled = enabled)
            userSettingsRepository.updateSettings(updatedSettings)
        }
    }

    fun updateVibrationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            val currentSettings = _userSettings.value ?: UserSettings()
            val updatedSettings = currentSettings.copy(vibrationEnabled = enabled)
            userSettingsRepository.updateSettings(updatedSettings)
        }
    }

    fun updateNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            val currentSettings = _userSettings.value ?: UserSettings()
            val updatedSettings = currentSettings.copy(notificationsEnabled = enabled)
            userSettingsRepository.updateSettings(updatedSettings)
            if (enabled) {
                notificationManager.updateReminderSchedule()
            } else {
                notificationManager.cancelReminders()
            }
        }
    }

    fun showNotificationPreview() {
        viewModelScope.launch {
            val currentSettings = _userSettings.value ?: UserSettings()
            notificationManager.showPreviewNotification(
                soundEnabled = currentSettings.soundEnabled,
                vibrationEnabled = currentSettings.vibrationEnabled
            )
        }
    }

    private fun initializeNotifications() {
        viewModelScope.launch {
            notificationManager.updateReminderSchedule()
        }
    }
}
