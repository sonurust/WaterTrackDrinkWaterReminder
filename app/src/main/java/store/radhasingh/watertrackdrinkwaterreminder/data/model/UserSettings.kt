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
    val notificationsEnabled: Boolean = true
)
