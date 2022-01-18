package ru.glushko.dnkstockapp.domain.repositories

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.model.Item

interface ItemRepository {
    fun getConsumablesItems(): LiveData<List<Item>>
    fun getHardwareItems(): LiveData<List<Item>>
    suspend fun addItem(item: Item)
    suspend fun deleteItem(item: Item)
    suspend fun updateItem(item: Item)
}