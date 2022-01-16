package ru.glushko.dnkstockapp.domain.repositories

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.entity.StockItem

interface StockItemRepository {
    fun loadAllStockItems(): LiveData<List<StockItem>>
    suspend fun addStockItem(stockItem: StockItem)
    suspend fun deleteStockItem(stockItem: StockItem)
    suspend fun updateStockItem(stockItem: StockItem)
}