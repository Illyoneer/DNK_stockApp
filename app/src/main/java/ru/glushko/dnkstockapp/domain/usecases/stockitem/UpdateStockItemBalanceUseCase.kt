package ru.glushko.dnkstockapp.domain.usecases.stockitem

import ru.glushko.dnkstockapp.domain.repositories.StockItemRepository

class UpdateStockItemBalanceUseCase(private val _stockItemRepository: StockItemRepository) {
    suspend fun updateStockItemBalance(incoming_count: Int, stock_item_name: String) =
        _stockItemRepository.updateStockItemBalance(incoming_count, stock_item_name)
}