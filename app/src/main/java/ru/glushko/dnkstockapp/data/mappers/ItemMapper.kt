package ru.glushko.dnkstockapp.data.mappers

import ru.glushko.dnkstockapp.data.model.DBItem
import ru.glushko.dnkstockapp.domain.Item

class ItemMapper {

    fun mapEntityToDBItem(item: Item) = DBItem(
        id = item.id,
        name = item.name,
        count = item.count,
        date = item.date,
        user = item.user,
        type = item.type
    ) //Мап элемента из Entity -> DB

    private fun mapDBItemToEntity(dbItem: DBItem) = Item(
        id = dbItem.id,
        name = dbItem.name,
        count = dbItem.count,
        date = dbItem.date,
        user = dbItem.user,
        type = dbItem.type
    ) //Мап элемента из DB -> Entity

    fun mapListDBItemToListEntity(list: List<DBItem>) = list.map{
        mapDBItemToEntity(it)
    } //Мап каждого элемента списка из DB -> Entity и получение нового списка.


}