package ru.glushko.dnkstockapp.data.provider

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.glushko.dnkstockapp.data.model.DBItem
import ru.glushko.dnkstockapp.domain.Item

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addItem(dbItem: DBItem)

    @Query("SELECT * FROM items_new")
    fun loadAllItems() : LiveData<List<DBItem>>

    @Delete
    fun deleteItem(dbItem: DBItem)

    @Update
    fun updateItem(dbItem: DBItem)

}