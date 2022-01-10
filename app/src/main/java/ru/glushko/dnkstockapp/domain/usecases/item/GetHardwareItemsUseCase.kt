package ru.glushko.dnkstockapp.domain.usecases.item

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.ItemRepository

class GetHardwareItemsUseCase(private val _itemRepository: ItemRepository) {
    fun getHardwareItems(): LiveData<List<Item>> = _itemRepository.getHardwareItems()
}