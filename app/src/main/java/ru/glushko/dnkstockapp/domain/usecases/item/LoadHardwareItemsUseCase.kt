package ru.glushko.dnkstockapp.domain.usecases.item

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.model.Item
import ru.glushko.dnkstockapp.domain.repositories.ItemRepository

class LoadHardwareItemsUseCase(private val _itemRepository: ItemRepository) {
    fun getHardwareItems(): LiveData<List<Item>> = _itemRepository.getHardwareItems()
}