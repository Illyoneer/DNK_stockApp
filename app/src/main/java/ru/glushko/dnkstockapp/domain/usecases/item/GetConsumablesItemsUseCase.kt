package ru.glushko.dnkstockapp.domain.usecases.item

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.ItemRepository

class GetConsumablesItemsUseCase(private val _itemRepository: ItemRepository) {
    fun getConsumablesItems(): LiveData<List<Item>> = _itemRepository.getConsumablesItems()
}