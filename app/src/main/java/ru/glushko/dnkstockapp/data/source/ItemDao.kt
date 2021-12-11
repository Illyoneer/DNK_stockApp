package ru.glushko.dnkstockapp.data.source

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.glushko.dnkstockapp.data.model.DBItem

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(dbItem: DBItem)

    @Query("SELECT * FROM items_test")
    fun loadAllItems(): LiveData<List<DBItem>>

    @Delete
    suspend fun deleteItem(dbItem: DBItem)

    @Update
    suspend fun updateItem(dbItem: DBItem)

}