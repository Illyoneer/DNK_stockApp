package ru.glushko.dnkstockapp.domain.usecases.stockitem

import ru.glushko.dnkstockapp.domain.model.StockItem
import ru.glushko.dnkstockapp.domain.repositories.StockItemRepository

class AddStockItemUseCase(private val _stockItemRepository: StockItemRepository) {
    suspend fun addStockItem(stockItem: StockItem) = _stockItemRepository.addStockItem(stockItem)
}