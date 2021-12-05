package ru.glushko.dnkstockapp.domain

import androidx.lifecycle.LiveData

interface ItemRepository {
    fun getItemsList():LiveData<List<Item>>
    suspend fun addItem(item: Item)
    suspend fun deleteItem(item: Item)
    suspend fun updateItem(item: Item)
}