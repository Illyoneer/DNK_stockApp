package ru.glushko.dnkstockapp.domain.usecases

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.Item

class GetItemsListUseCase (private val itemRepository: ItemRepository) {
    fun getItemsList(): LiveData<List<Item>> {
        return itemRepository.getItemsList()
    }
}