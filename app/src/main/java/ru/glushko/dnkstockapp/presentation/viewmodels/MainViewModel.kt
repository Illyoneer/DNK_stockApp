package ru.glushko.dnkstockapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.glushko.dnkstockapp.app.AppInstance
import ru.glushko.dnkstockapp.data.ItemRepositoryImpl
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.usecases.AddItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.DeleteItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.GetItemsListUseCase
import ru.glushko.dnkstockapp.domain.usecases.UpdateItemUseCase
import ru.glushko.dnkstockapp.utils.Status

class MainViewModel : ViewModel() {

    private val _repository = ItemRepositoryImpl(AppInstance.instance)

    private val _getItemsListUseCase = GetItemsListUseCase(_repository)
    private val _deleteItemUseCase = DeleteItemUseCase(_repository)
    private val _updateItemUseCase = UpdateItemUseCase(_repository)
    private val _addItemUseCase = AddItemUseCase(_repository)

    private val _stateAddItemLiveData = MutableLiveData<Status>()
    private val _stateEditItemLiveData = MutableLiveData<Status>()


    fun getStateAddItemLiveData(): MutableLiveData<Status> = _stateAddItemLiveData

    fun getStateEditItemLiveData(): MutableLiveData<Status> = _stateEditItemLiveData

    fun getItemsList(): LiveData<List<Item>> = _getItemsListUseCase.getItemsList()

    fun addItemToDatabase(name: String, count: String, date: String, user: String) {
        if (name.isNotEmpty() && count.isNotEmpty() && date.isNotEmpty() && user.isNotEmpty()) {
            viewModelScope.launch {
                _addItemUseCase.addItem(
                    Item(
                        name = name,
                        count = count,
                        date = date,
                        user = user
                    )
                )
            }
            _stateAddItemLiveData.postValue(Status.SUCCESS)
        } else {
            _stateAddItemLiveData.postValue(Status.ERROR)
        }
    }

    fun deleteItemFromDatabase(item: Item) = viewModelScope.launch {
        _deleteItemUseCase.deleteItem(item)
    }

    fun updateItemInDatabase(id: Int, name: String, count: String, date: String, user: String) {
        if (name.isNotEmpty() && count.isNotEmpty() && date.isNotEmpty() && user.isNotEmpty()) {
            viewModelScope.launch {
                _updateItemUseCase.updateItem(
                    Item(
                        id = id,
                        name = name,
                        count = count,
                        date = date,
                        user = user
                    )
                )
            }
            _stateEditItemLiveData.postValue(Status.SUCCESS)
        } else {
            _stateEditItemLiveData.postValue(Status.ERROR)
        }
    }
}