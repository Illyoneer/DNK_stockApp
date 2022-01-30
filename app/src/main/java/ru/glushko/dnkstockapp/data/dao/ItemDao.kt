package ru.glushko.dnkstockapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.glushko.dnkstockapp.data.model.DBItem

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(dbItem: DBItem)

    @Delete
    suspend fun deleteItem(dbItem: DBItem)

    @Update
    suspend fun updateItem(dbItem: DBItem)

    @Query("UPDATE stock_items2 SET stock_item_balance = (stock_item_balance - :item_count) WHERE stock_item_name = :item_name")
    suspend fun updateStockCountItemAfterAdd(item_name: String, item_count: Int)

    @Query("UPDATE stock_items2 SET stock_item_balance = ((stock_item_balance + :item_start_count) - :item_count) WHERE stock_item_name = :item_name")
    suspend fun updateStockCountItemAfterUpdate(item_name: String, item_count: Int, item_start_count:Int)

    @Query("UPDATE stock_items2 SET stock_item_balance = (stock_item_balance + :item_count) WHERE stock_item_name = :item_name")
    suspend fun updateStockCountItemAfterDelete(item_name: String, item_count: Int)

    @Query("SELECT * FROM split_items WHERE item_type = 'hardware'")
    fun loadHardwareItems(): LiveData<List<DBItem>>

    @Query("SELECT * FROM split_items WHERE item_type = 'consumables'")
    fun loadConsumablesItems(): LiveData<List<DBItem>>

    @Transaction
    suspend fun addItemWithUpdateStock(dbItem: DBItem){
        addItem(dbItem)
        updateStockCountItemAfterAdd(dbItem.name, dbItem.count)
    }

    @Transaction
    suspend fun updateItemWithUpdateStock(dbItem: DBItem, start_count:Int){
        updateItem(dbItem)
        updateStockCountItemAfterUpdate(dbItem.name, dbItem.count, start_count)
    }

    @Transaction
    suspend fun deleteItemWithUpdateStock(dbItem: DBItem){
        deleteItem(dbItem)
        updateStockCountItemAfterDelete(dbItem.name, dbItem.count)
    }


}