package ru.glushko.dnkstockapp.domain.usecases

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.ItemRepository

class GetHardwareItemsUseCase constructor(private val itemRepository: ItemRepository) {
    fun getHardwareItems(): LiveData<List<Item>> = itemRepository.getHardwareItems()
}