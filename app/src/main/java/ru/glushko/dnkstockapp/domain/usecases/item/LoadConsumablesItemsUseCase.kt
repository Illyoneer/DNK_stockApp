package ru.glushko.dnkstockapp.domain.usecases.item

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.ItemRepository

class LoadConsumablesItemsUseCase(private val _itemRepository: ItemRepository) {
    fun getConsumablesItems(): LiveData<List<Item>> = _itemRepository.getConsumablesItems()
}