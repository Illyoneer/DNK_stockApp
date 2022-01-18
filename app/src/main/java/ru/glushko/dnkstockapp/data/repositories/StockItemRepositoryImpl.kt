package ru.glushko.dnkstockapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.glushko.dnkstockapp.data.dao.StockItemDao
import ru.glushko.dnkstockapp.data.mappers.StockItemMapper
import ru.glushko.dnkstockapp.domain.model.StockItem
import ru.glushko.dnkstockapp.domain.repositories.StockItemRepository

class StockItemRepositoryImpl(
    private val _stockItemDao: StockItemDao,
    private val _mapper: StockItemMapper
) : StockItemRepository {

    override fun loadAllStockItems(): LiveData<List<StockItem>> =
        Transformations.map(_stockItemDao.loadAllStockItems()) {
            _mapper.mapListDBStockItemToListEntity(it)
        }

    override suspend fun addStockItem(stockItem: StockItem) =
        _stockItemDao.addStockItem(_mapper.mapEntityToDBStockItem(stockItem))

    override suspend fun deleteStockItem(stockItem: StockItem) =
        _stockItemDao.deleteStockItem(_mapper.mapEntityToDBStockItem(stockItem))

    override suspend fun updateStockItem(stockItem: StockItem) =
        _stockItemDao.updateStockItem(_mapper.mapEntityToDBStockItem(stockItem))

}