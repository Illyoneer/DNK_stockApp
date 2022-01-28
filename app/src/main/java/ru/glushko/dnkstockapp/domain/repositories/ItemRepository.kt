package ru.glushko.dnkstockapp.domain.repositories

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.model.Item

interface ItemRepository {
    fun getConsumablesItems(): LiveData<List<Item>>
    fun getHardwareItems(): LiveData<List<Item>>
    suspend fun addItem(item: Item)
    suspend fun deleteItemWithUpdateStock(item: Item)
    suspend fun updateItemWithUpdateStock(item: Item, start_count:Int)
    suspend fun addItemWithUpdateStock(item: Item)
}