package ru.glushko.dnkstockapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.glushko.dnkstockapp.data.mapper.ItemMapper
import ru.glushko.dnkstockapp.data.source.ItemDao
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.ItemRepository

class ItemRepositoryImpl constructor(
    private val _itemDao: ItemDao,
    private val mapper: ItemMapper
) : ItemRepository {

    /*private val _itemDao: ItemDao = ItemsDatabase.getInstance(AppInstance.instance).userDao()*/
    override fun getItemsList(): LiveData<List<Item>> =
        Transformations.map(_itemDao.loadAllItems()) {
            mapper.mapListDBItemToListEntity(it)
        }

    override suspend fun addItem(item: Item) = _itemDao.addItem(mapper.mapEntityToDBItem(item))

    override suspend fun deleteItem(item: Item) =
        _itemDao.deleteItem(mapper.mapEntityToDBItem(item))

    override suspend fun updateItem(item: Item) =
        _itemDao.updateItem(mapper.mapEntityToDBItem(item))

}