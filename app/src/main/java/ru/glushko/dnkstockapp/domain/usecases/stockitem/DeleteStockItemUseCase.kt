package ru.glushko.dnkstockapp.domain.usecases.stockitem

import ru.glushko.dnkstockapp.domain.entity.StockItem
import ru.glushko.dnkstockapp.domain.repositories.StockItemRepository

class DeleteStockItemUseCase (private val _stockItemRepository: StockItemRepository){
    suspend fun deleteStockItem(stockItem: StockItem) = _stockItemRepository.deleteStockItem(stockItem)
}