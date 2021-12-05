package ru.glushko.dnkstockapp.data

import ru.glushko.dnkstockapp.data.model.DBItem
import ru.glushko.dnkstockapp.domain.Item

class ItemMapper {

    fun mapEntityToDBItem(item: Item) = DBItem(
        id = item.id,
        name = item.name,
        count = item.count,
        date = item.date,
        user = item.user
    )

    fun mapDBItemToEntity(dbItem: DBItem) = Item(
        id = dbItem.id,
        name = dbItem.name,
        count = dbItem.count,
        date = dbItem.date,
        user = dbItem.user
    )

    fun mapListDBItemToListEntity(list: List<DBItem>) = list.map{
        mapDBItemToEntity(it)
    }


}