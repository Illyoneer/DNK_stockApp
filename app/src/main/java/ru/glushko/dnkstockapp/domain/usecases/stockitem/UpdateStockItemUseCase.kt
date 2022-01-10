package ru.glushko.dnkstockapp.domain.usecases.stockitem

import ru.glushko.dnkstockapp.domain.StockItem
import ru.glushko.dnkstockapp.domain.StockItemRepository

class UpdateStockItemUseCase (private val _stockItemRepository: StockItemRepository){
    suspend fun updateStockItem(stockItem: StockItem) = _stockItemRepository.updateStockItem(stockItem)
}