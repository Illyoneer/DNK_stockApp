package ru.glushko.dnkstockapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.glushko.dnkstockapp.app.AppInstance
import ru.glushko.dnkstockapp.model.Item
import ru.glushko.dnkstockapp.model.provider.ItemDao
import ru.glushko.dnkstockapp.utils.Status

class MainViewModel : ViewModel() {
        private val _itemDao: ItemDao = AppInstance.instance.getDatabase().userDao()
        private val _itemsLiveData: LiveData<List<Item>> = _itemDao. loadAllItems()
        private val _stateAddItemLiveData = MutableLiveData<Status>()

        fun getItemsLiveData(): LiveData<List<Item>> { return _itemsLiveData }
        fun getStateAddItemLiveData(): MutableLiveData<Status> { return _stateAddItemLiveData }
        fun addItemToDatabase(name: String, count:String, date:String, user:String) {
                if(name.isNotEmpty() && count.isNotEmpty() && date.isNotEmpty() && user.isNotEmpty()) {
                        val item = Item(
                                0,
                                name = name,
                                count = count,
                                date = date,
                                user = user
                        )
                        _itemDao.addItem(item)
                        _stateAddItemLiveData.value = Status.SUCCESS
                }else { _stateAddItemLiveData.value = Status.ERROR }
        }

        fun deleteItemFromDatabase(item:Item) = _itemDao.deleteItem(item)
        fun updateItemInDatabase(item: Item) = _itemDao.updateItem(item)
}