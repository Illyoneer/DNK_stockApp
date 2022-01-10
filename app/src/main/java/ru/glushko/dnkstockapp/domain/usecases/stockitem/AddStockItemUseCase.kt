package ru.glushko.dnkstockapp.domain.usecases.stockitem

import ru.glushko.dnkstockapp.domain.StockItem
import ru.glushko.dnkstockapp.domain.StockItemRepository

class AddStockItemUseCase(private val _stockItemRepository: StockItemRepository) {
    suspend fun addStockItem(stockItem: StockItem) = _stockItemRepository.addStockItem(stockItem)
}