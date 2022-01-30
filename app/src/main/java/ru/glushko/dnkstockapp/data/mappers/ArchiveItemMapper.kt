package ru.glushko.dnkstockapp.data.mappers

import ru.glushko.dnkstockapp.data.model.DBArchiveItem
import ru.glushko.dnkstockapp.domain.model.ArchiveItem

class ArchiveItemMapper {
    fun mapEntityToDBArchiveItem(archiveItem: ArchiveItem) = DBArchiveItem(
        id = archiveItem.id,
        name = archiveItem.name,
        count = archiveItem.count,
        date = archiveItem.date,
        user = archiveItem.user,
        type = archiveItem.type
    ) //Мап элемента из Entity -> DB

    private fun mapDBArchiveItemToEntity(dbArchiveItem: DBArchiveItem) = ArchiveItem(
        id = dbArchiveItem.id,
        name = dbArchiveItem.name,
        count = dbArchiveItem.count,
        date = dbArchiveItem.date,
        user = dbArchiveItem.user,
        type = dbArchiveItem.type
    ) //Мап элемента из DB -> Entity

    fun mapListDBArchiveItemToListEntity(list: List<DBArchiveItem>) = list.map{
        mapDBArchiveItemToEntity(it)
    } //Мап каждого элемента списка из DB -> Entity и получение нового списка.
}