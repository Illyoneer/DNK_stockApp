package ru.glushko.dnkstockapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.glushko.dnkstockapp.data.provider.ItemDao
import ru.glushko.dnkstockapp.data.provider.ItemsDatabase
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.usecases.ItemRepository

class ItemRepositoryImpl(application: Application) : ItemRepository {

    private val _itemDao: ItemDao = ItemsDatabase.getInstance(application).userDao()
    private val _mapper = ItemMapper()

    override fun getItemsList(): LiveData<List<Item>> =
        Transformations.map(_itemDao.loadAllItems()) {
            _mapper.mapListDBItemToListEntity(it)
        }

    override suspend fun addItem(item: Item) {
        _itemDao.addItem(_mapper.mapEntityToDBItem(item))
    }

    override suspend fun deleteItem(item: Item) {
        _itemDao.deleteItem(_mapper.mapEntityToDBItem(item))
    }

    override suspend fun updateItem(item: Item) {
        _itemDao.updateItem(_mapper.mapEntityToDBItem(item))
    }
}