package ru.glushko.dnkstockapp.domain.repositories

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.model.InventoryItem

interface InventoryItemRepository {
    fun loadAllInventoryItems(): LiveData<List<InventoryItem>>
    suspend fun addInventoryItem(inventoryItem: InventoryItem)
    suspend fun deleteInventoryItem(inventoryItem: InventoryItem)
    suspend fun updateInventoryItem(inventoryItem: InventoryItem) //TODO: Переделать.
}