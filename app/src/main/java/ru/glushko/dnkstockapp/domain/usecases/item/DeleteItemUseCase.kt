package ru.glushko.dnkstockapp.domain.usecases.item

import ru.glushko.dnkstockapp.domain.entity.Item
import ru.glushko.dnkstockapp.domain.repositories.ItemRepository

class DeleteItemUseCase(private val _itemRepository: ItemRepository) {
    suspend fun deleteItem(item: Item) = _itemRepository.deleteItem(item)
}