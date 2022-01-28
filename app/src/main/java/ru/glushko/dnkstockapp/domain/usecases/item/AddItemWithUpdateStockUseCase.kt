package ru.glushko.dnkstockapp.domain.usecases.item

import ru.glushko.dnkstockapp.domain.model.Item
import ru.glushko.dnkstockapp.domain.repositories.ItemRepository

class AddItemWithUpdateStockUseCase (private val _itemRepository: ItemRepository) {
    suspend fun addItemWithUpdateStock(item: Item) = _itemRepository.addItemWithUpdateStock(item)
}