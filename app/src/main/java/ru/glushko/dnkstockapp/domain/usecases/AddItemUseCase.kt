package ru.glushko.dnkstockapp.domain.usecases

import ru.glushko.dnkstockapp.domain.Item

class AddItemUseCase (private val itemRepository: ItemRepository){
    fun addItem(item: Item) {
        itemRepository.addItem(item)
    }
}