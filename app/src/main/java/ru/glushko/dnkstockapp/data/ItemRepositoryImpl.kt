package ru.glushko.dnkstockapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import ru.glushko.dnkstockapp.app.AppInstance
import ru.glushko.dnkstockapp.data.provider.ItemDao
import ru.glushko.dnkstockapp.data.provider.ItemsDatabase
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.usecases.ItemRepository

class ItemRepositoryImpl(application: Application) : ItemRepository {

    private val _itemDao: ItemDao = ItemsDatabase.getInstance(application).userDao()
    private val _mapper = ItemMapper()

    override fun getItemsList(): LiveData<List<Item>> {
       return MediatorLiveData<List<Item>>().apply {
           addSource(_itemDao.loadAllItems()){
               value = _mapper.mapListDBItemToListEntity(it)
           }
       }
    }

    override fun addItem(item: Item) {
        _itemDao.addItem(_mapper.mapEntityToDBItem(item))
    }

    override fun deleteItem(item: Item) {
        _itemDao.deleteItem(_mapper.mapEntityToDBItem(item))
    }

    override fun updateItem(item: Item) {
        _itemDao.updateItem(_mapper.mapEntityToDBItem(item))
    }
}