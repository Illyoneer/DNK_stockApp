package ru.glushko.dnkstockapp.domain.usecases.item

import ru.glushko.dnkstockapp.domain.model.Item
import ru.glushko.dnkstockapp.domain.repositories.ItemRepository

class DeleteItemWithUpdateStockUseCase(private val _itemRepository: ItemRepository) {
    suspend fun deleteItemWithUpdateStock(item: Item) = _itemRepository.deleteItemWithUpdateStock(item)
}
