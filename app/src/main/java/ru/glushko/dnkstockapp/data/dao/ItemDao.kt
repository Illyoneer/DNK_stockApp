package ru.glushko.dnkstockapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.glushko.dnkstockapp.data.model.DBItem

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(dbItem: DBItem)

    @Query("SELECT * FROM split_items WHERE item_type = 'hardware'")
    fun loadHardwareItems(): LiveData<List<DBItem>>

    @Query("SELECT * FROM split_items WHERE item_type = 'consumables'")
    fun loadConsumablesItems(): LiveData<List<DBItem>>

    @Delete
    suspend fun deleteItem(dbItem: DBItem)

    @Update
    suspend fun updateItem(dbItem: DBItem)

}