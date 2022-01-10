package ru.glushko.dnkstockapp.domain

import androidx.lifecycle.LiveData

interface StockItemRepository {
    fun loadAllStockItems(): LiveData<List<StockItem>>
    suspend fun addStockItem(stockItem: StockItem)
    suspend fun deleteStockItem(stockItem: StockItem)
    suspend fun updateStockItem(stockItem: StockItem)
}