package ru.glushko.dnkstockapp.domain.usecases

import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.ItemRepository

class AddItemUseCase (private val itemRepository: ItemRepository){
    suspend fun addItem(item: Item) = itemRepository.addItem(item)
}