package store.radhasingh.watertrackdrinkwaterreminder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "drink_entries")
data class DrinkEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val drinkType: String,
    val volume: Int, // in milliliters
    val timestamp: LocalDateTime
)

enum class DrinkType(val displayName: String, val emoji: String) {
    WATER("Water", "💧"),
    JUICE("Juice", "🧃"),
    TEA("Tea", "🍵"),
    COFFEE("Coffee", "☕"),
    MILK("Milk", "🥛"),
    SMOOTHIE("Smoothie", "🥤"),
    SODA("Soda", "🥤"),
    OTHER("Other", "🥤")
}

data class PresetVolume(
    val volume: Int,
    val displayName: String
) {
    companion object {
        val PRESET_VOLUMES = listOf(
            PresetVolume(100, "100ml"),
            PresetVolume(200, "200ml"),
            PresetVolume(300, "300ml"),
            PresetVolume(500, "500ml")
        )
    }
}
