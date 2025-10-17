package store.radhasingh.watertrackdrinkwaterreminder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey
    val id: Int = 1, // Single settings record
    val dailyGoal: Int = 2000, // Default 2L in milliliters
    val reminderIntervalMinutes: Int = 60, // Default 1 hour
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val notificationsEnabled: Boolean = true,
    val selectedNotificationSound: String = "soft_water_reminder", // Default notification sound
    val selectedButtonSound: String = "button_tap", // Default button sound
    val selectedGoalSound: String = "goal_achieved", // Default goal achieved sound
    val voiceEnabled: Boolean = true, // Enable voice prompts
    val ambientSoundEnabled: Boolean = false // Enable ambient background sounds
)
