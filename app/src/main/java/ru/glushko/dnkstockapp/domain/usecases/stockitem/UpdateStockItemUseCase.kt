package ru.glushko.dnkstockapp.domain.usecases.stockitem

import ru.glushko.dnkstockapp.domain.model.StockItem
import ru.glushko.dnkstockapp.domain.repositories.StockItemRepository

class UpdateStockItemUseCase (private val _stockItemRepository: StockItemRepository){
    suspend fun updateStockItem(stockItem: StockItem) = _stockItemRepository.updateStockItem(stockItem)
}