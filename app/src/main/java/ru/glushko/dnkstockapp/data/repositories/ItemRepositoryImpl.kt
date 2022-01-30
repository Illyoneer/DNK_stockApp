package ru.glushko.dnkstockapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.glushko.dnkstockapp.data.dao.ItemDao
import ru.glushko.dnkstockapp.data.mappers.ItemMapper
import ru.glushko.dnkstockapp.domain.model.Item
import ru.glushko.dnkstockapp.domain.repositories.ItemRepository

class ItemRepositoryImpl(
    private val _itemDao: ItemDao,
    private val _mapper: ItemMapper
) : ItemRepository {

    override fun getConsumablesItems(): LiveData<List<Item>> =
        Transformations.map(_itemDao.loadConsumablesItems()) {
            _mapper.mapListDBItemToListEntity(it)
        }

    override fun getHardwareItems(): LiveData<List<Item>> =
        Transformations.map(_itemDao.loadHardwareItems()) {
            _mapper.mapListDBItemToListEntity(it)
        }

    override suspend fun addItem(item: Item) =
        _itemDao.addItem(_mapper.mapEntityToDBItem(item))

    override suspend fun deleteItemWithUpdateStock(item: Item) =
        _itemDao.deleteItemWithUpdateStock(_mapper.mapEntityToDBItem(item))

    override suspend fun updateItemWithUpdateStock(item: Item, start_count:Int) =
        _itemDao.updateItemWithUpdateStock(_mapper.mapEntityToDBItem(item), start_count)

    override suspend fun addItemWithUpdateStock(item: Item) =
        _itemDao.addItemWithUpdateStock(_mapper.mapEntityToDBItem(item))
}