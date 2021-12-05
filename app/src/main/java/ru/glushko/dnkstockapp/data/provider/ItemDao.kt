package ru.glushko.dnkstockapp.data.provider

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.glushko.dnkstockapp.data.model.DBItem

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(dbItem: DBItem)

    @Query("SELECT * FROM items_new")
    fun loadAllItems(): LiveData<List<DBItem>>

    @Delete
    suspend fun deleteItem(dbItem: DBItem)

    @Update
    suspend fun updateItem(dbItem: DBItem)

}