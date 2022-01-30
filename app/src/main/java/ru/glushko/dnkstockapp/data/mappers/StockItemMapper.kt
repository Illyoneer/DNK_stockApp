package ru.glushko.dnkstockapp.data.mappers

import ru.glushko.dnkstockapp.data.model.DBStockItem
import ru.glushko.dnkstockapp.domain.model.StockItem

class StockItemMapper {

    fun mapEntityToDBStockItem(stockItem: StockItem) = DBStockItem(
        id = stockItem.id,
        name = stockItem.name,
        count = stockItem.count,
        balance = stockItem.balance
    ) //Мап элемента из Entity -> DB

    private fun mapDBStockItemToEntity(dbStockItem: DBStockItem) = StockItem(
        id = dbStockItem.id,
        name = dbStockItem.name,
        count = dbStockItem.count,
        balance = dbStockItem.balance
    ) //Мап элемента из DB -> Entity

    fun mapListDBStockItemToListEntity(list: List<DBStockItem>) = list.map{
        mapDBStockItemToEntity(it)
    } //Мап каждого элемента списка из DB -> Entity и получение нового списка.

}