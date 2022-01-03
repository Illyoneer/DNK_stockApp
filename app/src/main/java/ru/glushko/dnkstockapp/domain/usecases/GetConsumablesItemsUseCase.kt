package ru.glushko.dnkstockapp.domain.usecases

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.ItemRepository

class GetConsumablesItemsUseCase constructor(private val itemRepository: ItemRepository) {
    fun getConsumablesItems(): LiveData<List<Item>> = itemRepository.getConsumablesItems()
}