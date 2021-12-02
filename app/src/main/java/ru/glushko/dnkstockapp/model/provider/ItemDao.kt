package ru.glushko.dnkstockapp.model.provider

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.glushko.dnkstockapp.model.Item

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addItem(item: Item)

    @Query("SELECT * FROM items_new")
    fun loadAllItems() : LiveData<List<Item>>

    @Delete
    fun deleteItem(item: Item)

    @Update
    fun updateItem(item:Item)

}