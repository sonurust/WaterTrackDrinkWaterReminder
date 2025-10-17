package store.radhasingh.watertrackdrinkwaterreminder.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import store.radhasingh.watertrackdrinkwaterreminder.data.model.DrinkEntry

@Dao
interface DrinkEntryDao {
    @Query("SELECT * FROM drink_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<DrinkEntry>>

    @Query("SELECT * FROM drink_entries WHERE date(timestamp) = date(:dateString) ORDER BY timestamp DESC")
    fun getEntriesByDate(dateString: String): Flow<List<DrinkEntry>>

    @Query("SELECT SUM(volume) FROM drink_entries WHERE date(timestamp) = date(:dateString)")
    fun getTotalVolumeByDate(dateString: String): Flow<Int?>

    @Query("SELECT * FROM drink_entries WHERE date(timestamp) >= date(:startDateString) AND date(timestamp) <= date(:endDateString) ORDER BY timestamp DESC")
    fun getEntriesBetweenDates(startDateString: String, endDateString: String): Flow<List<DrinkEntry>>

    @Insert
    suspend fun insertEntry(entry: DrinkEntry): Long

    @Update
    suspend fun updateEntry(entry: DrinkEntry)

    @Delete
    suspend fun deleteEntry(entry: DrinkEntry)

    @Query("DELETE FROM drink_entries WHERE id = :id")
    suspend fun deleteEntryById(id: Long)

    @Query("DELETE FROM drink_entries")
    suspend fun deleteAllEntries()
}
