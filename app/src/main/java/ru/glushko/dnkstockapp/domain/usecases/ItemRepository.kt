package ru.glushko.dnkstockapp.domain.usecases

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.Item

interface ItemRepository {
    fun getItemsList():LiveData<List<Item>>
    fun addItem(item: Item)
    fun deleteItem(item: Item)
    fun updateItem(item: Item)
}