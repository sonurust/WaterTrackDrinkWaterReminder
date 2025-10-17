package store.radhasingh.watertrackdrinkwaterreminder.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import store.radhasingh.watertrackdrinkwaterreminder.data.model.UserSettings

@Dao
interface UserSettingsDao {
    @Query("SELECT * FROM user_settings WHERE id = 1")
    fun getSettings(): Flow<UserSettings?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: UserSettings)

    @Update
    suspend fun updateSettings(settings: UserSettings)

    @Query("DELETE FROM user_settings")
    suspend fun deleteAllSettings()
}
