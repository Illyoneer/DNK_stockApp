package ru.glushko.dnkstockapp.domain.usecases

import ru.glushko.dnkstockapp.domain.Item

class DeleteItemUseCase (private val itemRepository: ItemRepository){
    suspend fun deleteItem(item: Item) {
        itemRepository.deleteItem(item)
    }
}