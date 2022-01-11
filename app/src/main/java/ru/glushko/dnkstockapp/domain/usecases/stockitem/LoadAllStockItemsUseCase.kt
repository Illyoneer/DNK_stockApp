package ru.glushko.dnkstockapp.domain.usecases.stockitem

import androidx.lifecycle.LiveData
import ru.glushko.dnkstockapp.domain.entity.StockItem
import ru.glushko.dnkstockapp.domain.repositories.StockItemRepository

class LoadAllStockItemsUseCase (private val _stockItemRepository: StockItemRepository) {
    fun loadAllStockItems(): LiveData<List<StockItem>> = _stockItemRepository.loadAllStockItems()
}