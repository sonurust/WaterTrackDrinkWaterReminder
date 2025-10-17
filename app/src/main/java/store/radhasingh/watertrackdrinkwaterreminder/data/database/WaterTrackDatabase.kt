package store.radhasingh.watertrackdrinkwaterreminder.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import store.radhasingh.watertrackdrinkwaterreminder.data.dao.DrinkEntryDao
import store.radhasingh.watertrackdrinkwaterreminder.data.dao.UserSettingsDao
import store.radhasingh.watertrackdrinkwaterreminder.data.model.DrinkEntry
import store.radhasingh.watertrackdrinkwaterreminder.data.model.UserSettings
import store.radhasingh.watertrackdrinkwaterreminder.data.converter.LocalDateTimeConverter

@Database(
    entities = [DrinkEntry::class, UserSettings::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class WaterTrackDatabase : RoomDatabase() {
    abstract fun drinkEntryDao(): DrinkEntryDao
    abstract fun userSettingsDao(): UserSettingsDao

    companion object {
        @Volatile
        private var INSTANCE: WaterTrackDatabase? = null

        fun getDatabase(context: Context): WaterTrackDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WaterTrackDatabase::class.java,
                    "water_track_database"
                )
                .fallbackToDestructiveMigration() // For development - clears data on schema change
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
