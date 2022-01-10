package ru.glushko.dnkstockapp.domain.usecases.item

import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.ItemRepository

class UpdateItemUseCase(private val _itemRepository: ItemRepository){
    suspend fun updateItem(item: Item) = _itemRepository.updateItem(item)
}