package ru.glushko.dnkstockapp.domain.usecases.item

import ru.glushko.dnkstockapp.domain.model.Item
import ru.glushko.dnkstockapp.domain.repositories.ItemRepository

class UpdateItemWithUpdateStockUseCases(private val _itemRepository: ItemRepository){
    suspend fun updateItem(item: Item, start_count:Int) = _itemRepository.updateItemWithUpdateStock(item, start_count)
}