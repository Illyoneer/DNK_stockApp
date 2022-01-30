package ru.glushko.dnkstockapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.glushko.dnkstockapp.data.model.DBStockItem

@Dao
interface StockItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStockItem(dbStockItem: DBStockItem)

    @Query("SELECT * FROM stock_items2")
    fun loadAllStockItems(): LiveData<List<DBStockItem>>

    @Delete
    suspend fun deleteStockItem(dbStockItem: DBStockItem)

    @Update
    suspend fun updateStockItem(dbStockItem: DBStockItem)

    @Query("UPDATE stock_items2 SET stock_item_balance = (stock_item_balance + :incoming_count) WHERE stock_item_name = :stock_item_name")
    suspend fun updateStockItemBalance(incoming_count: Int, stock_item_name:String)
}