package ru.glushko.dnkstockapp.domain.usecases

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.ItemRepository

class GetItemsListUseCase constructor
    (private val itemRepository: ItemRepository) {
    fun getItemsList(): LiveData<List<Item>> = itemRepository.getItemsList()
}