package ru.glushko.dnkstockapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.glushko.dnkstockapp.data.model.DBStockItem

@Dao
interface StockItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStockItem(dbStockItem: DBStockItem)

    @Query("SELECT * FROM stock_items")
    fun loadAllStockItems(): LiveData<List<DBStockItem>>

    @Delete
    suspend fun deleteStockItem(dbStockItem: DBStockItem)

    @Update
    suspend fun updateStockItem(dbStockItem: DBStockItem)
}