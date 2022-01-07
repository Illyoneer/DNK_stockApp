package ru.glushko.dnkstockapp.domain

import androidx.lifecycle.LiveData

interface ItemRepository {
    fun getConsumablesItems(): LiveData<List<Item>>
    fun getHardwareItems(): LiveData<List<Item>>
    suspend fun addItem(item: Item)
    suspend fun deleteItem(item: Item)
    suspend fun updateItem(item: Item)
}