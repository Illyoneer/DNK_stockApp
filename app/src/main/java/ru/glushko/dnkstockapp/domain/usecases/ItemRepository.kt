package ru.glushko.dnkstockapp.domain.usecases

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.Item

interface ItemRepository {
    fun getItemsList():LiveData<List<Item>>
    suspend fun addItem(item: Item)
    suspend fun deleteItem(item: Item)
    suspend fun updateItem(item: Item)
}