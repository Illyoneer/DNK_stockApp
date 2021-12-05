package ru.glushko.dnkstockapp.domain.usecases

import ru.glushko.dnkstockapp.domain.Item

class UpdateItemUseCase (private val itemRepository: ItemRepository){
    fun updateItem(item: Item) {
        itemRepository.updateItem(item)
    }
}