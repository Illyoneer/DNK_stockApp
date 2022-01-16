package ru.glushko.dnkstockapp.domain.usecases.item

import ru.glushko.dnkstockapp.domain.entity.Item
import ru.glushko.dnkstockapp.domain.repositories.ItemRepository

class UpdateItemUseCase(private val _itemRepository: ItemRepository){
    suspend fun updateItem(item: Item) = _itemRepository.updateItem(item)
}