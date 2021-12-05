package ru.glushko.dnkstockapp.domain.usecases

import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.ItemRepository

class UpdateItemUseCase (private val itemRepository: ItemRepository){
    suspend fun updateItem(item: Item) = itemRepository.updateItem(item)
}