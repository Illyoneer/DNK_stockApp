package ru.glushko.dnkstockapp.domain.usecases.stockitem

import ru.glushko.dnkstockapp.domain.StockItem
import ru.glushko.dnkstockapp.domain.StockItemRepository

class DeleteStockItemUseCase (private val _stockItemRepository: StockItemRepository){
    suspend fun deleteStockItem(stockItem: StockItem) = _stockItemRepository.deleteStockItem(stockItem)
}