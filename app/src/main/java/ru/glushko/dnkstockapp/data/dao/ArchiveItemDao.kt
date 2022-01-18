package ru.glushko.dnkstockapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.glushko.dnkstockapp.data.model.DBArchiveItem

@Dao
interface ArchiveItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArchiveItem(dbArchiveItem: DBArchiveItem)

    @Query("SELECT * FROM archive_items")
    fun loadAllArchiveItems(): LiveData<List<DBArchiveItem>>

    @Delete
    suspend fun deleteArchiveItem(dbArchiveItem: DBArchiveItem)
}