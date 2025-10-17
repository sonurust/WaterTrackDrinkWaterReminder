package store.radhasingh.watertrackdrinkwaterreminder.data.repository

import kotlinx.coroutines.flow.Flow
import store.radhasingh.watertrackdrinkwaterreminder.data.dao.DrinkEntryDao
import store.radhasingh.watertrackdrinkwaterreminder.data.model.DrinkEntry
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DrinkEntryRepository(private val drinkEntryDao: DrinkEntryDao) {
    
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    
    fun getAllEntries(): Flow<List<DrinkEntry>> = drinkEntryDao.getAllEntries()
    
    fun getEntriesByDate(date: LocalDate): Flow<List<DrinkEntry>> = 
        drinkEntryDao.getEntriesByDate(date.format(dateFormatter))
    
    fun getTotalVolumeByDate(date: LocalDate): Flow<Int?> = 
        drinkEntryDao.getTotalVolumeByDate(date.format(dateFormatter))
    
    fun getEntriesBetweenDates(startDate: LocalDate, endDate: LocalDate): Flow<List<DrinkEntry>> = 
        drinkEntryDao.getEntriesBetweenDates(
            startDate.format(dateFormatter), 
            endDate.format(dateFormatter)
        )
    
    suspend fun insertEntry(entry: DrinkEntry): Long = drinkEntryDao.insertEntry(entry)
    
    suspend fun updateEntry(entry: DrinkEntry) = drinkEntryDao.updateEntry(entry)
    
    suspend fun deleteEntry(entry: DrinkEntry) = drinkEntryDao.deleteEntry(entry)
    
    suspend fun deleteEntryById(id: Long) = drinkEntryDao.deleteEntryById(id)
    
    suspend fun deleteAllEntries() = drinkEntryDao.deleteAllEntries()
}
