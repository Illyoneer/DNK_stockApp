package ru.glushko.dnkstockapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.glushko.dnkstockapp.data.ItemRepositoryImpl
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.app.AppInstance
import ru.glushko.dnkstockapp.domain.usecases.AddItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.DeleteItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.GetItemsListUseCase
import ru.glushko.dnkstockapp.domain.usecases.UpdateItemUseCase
import ru.glushko.dnkstockapp.presentation.utils.Status

class MainViewModel : ViewModel() {

    private val _repository = ItemRepositoryImpl(AppInstance.instance)

    private val _getItemsListUseCase = GetItemsListUseCase(_repository)
    private val _deleteItemUseCase = DeleteItemUseCase(_repository)
    private val _updateItemUseCase = UpdateItemUseCase(_repository)
    private val _addItemUseCase = AddItemUseCase(_repository)

    private val _stateAddItemLiveData = MutableLiveData<Status>()
    private val _stateEditItemLiveData = MutableLiveData<Status>()


    fun getStateAddItemLiveData(): MutableLiveData<Status> {
        return _stateAddItemLiveData
    }

    fun getStateEditItemLiveData(): MutableLiveData<Status> {
        return _stateEditItemLiveData
    }

    fun getItemsList(): LiveData<List<Item>> {
        return _getItemsListUseCase.getItemsList()
    }

    fun addItemToDatabase(name: String, count: String, date: String, user: String) {
        if (name.isNotEmpty() && count.isNotEmpty() && date.isNotEmpty() && user.isNotEmpty()) {
            _addItemUseCase.addItem(
                Item(
                    name = name,
                    count = count,
                    date = date,
                    user = user
                )
            )

            _stateAddItemLiveData.value = Status.SUCCESS
        } else {
            _stateAddItemLiveData.value = Status.ERROR
        }
    }

    fun deleteItemFromDatabase(item: Item) = _deleteItemUseCase.deleteItem(item)

    fun updateItemInDatabase(id: Int, name: String, count: String, date: String, user: String) {
        if (name.isNotEmpty() && count.isNotEmpty() && date.isNotEmpty() && user.isNotEmpty()) {
            _updateItemUseCase.updateItem(
                Item(
                    id = id,
                    name = name,
                    count = count,
                    date = date,
                    user = user
                )
            )

            _stateEditItemLiveData.value = Status.SUCCESS
        } else {
            _stateEditItemLiveData.value = Status.ERROR
        }
    }
}