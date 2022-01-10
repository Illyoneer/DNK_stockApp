package ru.glushko.dnkstockapp.domain.usecases.item

import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.ItemRepository

class AddItemUseCase(private val _itemRepository: ItemRepository) {
    suspend fun addItem(item: Item) = _itemRepository.addItem(item)
}