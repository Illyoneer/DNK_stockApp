package ru.glushko.dnkstockapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.glushko.dnkstockapp.data.mappers.ItemMapper
import ru.glushko.dnkstockapp.data.source.ItemDao
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.ItemRepository

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

    override suspend fun addItem(item: Item) = _itemDao.addItem(_mapper.mapEntityToDBItem(item))

    override suspend fun deleteItem(item: Item) =
        _itemDao.deleteItem(_mapper.mapEntityToDBItem(item))

    override suspend fun updateItem(item: Item) =
        _itemDao.updateItem(_mapper.mapEntityToDBItem(item))

}