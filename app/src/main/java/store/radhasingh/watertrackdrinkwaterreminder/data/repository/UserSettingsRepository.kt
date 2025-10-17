package store.radhasingh.watertrackdrinkwaterreminder.data.repository

import kotlinx.coroutines.flow.Flow
import store.radhasingh.watertrackdrinkwaterreminder.data.dao.UserSettingsDao
import store.radhasingh.watertrackdrinkwaterreminder.data.model.UserSettings

class UserSettingsRepository(private val userSettingsDao: UserSettingsDao) {
    
    fun getSettings(): Flow<UserSettings?> = userSettingsDao.getSettings()
    
    suspend fun insertSettings(settings: UserSettings) = userSettingsDao.insertSettings(settings)
    
    suspend fun updateSettings(settings: UserSettings) = userSettingsDao.updateSettings(settings)
    
    suspend fun deleteAllSettings() = userSettingsDao.deleteAllSettings()
}
