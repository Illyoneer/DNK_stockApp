package ru.glushko.dnkstockapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.usecases.*
import ru.glushko.dnkstockapp.presentation.viewutils.Status

class MainViewModel constructor(
    private val _getItemsListUseCase: GetItemsListUseCase,
    private val _deleteItemUseCase:DeleteItemUseCase,
    private val _updateItemUseCase: UpdateItemUseCase,
    private val _addItemUseCase:AddItemUseCase,
    private val _getConsumablesItemsUseCase:GetConsumablesItemsUseCase,
    private val _getHardwareItemsUseCase:GetHardwareItemsUseCase,
) : ViewModel() {

    private val _stateAddItemLiveData = MutableLiveData<Status>()
    private val _stateEditItemLiveData = MutableLiveData<Status>()


    fun getStateAddItemLiveData(): MutableLiveData<Status> = _stateAddItemLiveData

    fun getStateEditItemLiveData(): MutableLiveData<Status> = _stateEditItemLiveData

    fun getItemsList(): LiveData<List<Item>> = _getItemsListUseCase.getItemsList()

    fun getGetConsumablesItems(): LiveData<List<Item>> = _getConsumablesItemsUseCase.getConsumablesItems()

    fun getGetHardwareItems(): LiveData<List<Item>> = _getHardwareItemsUseCase.getHardwareItems()

    fun addItemToDatabase(name: String, count: String, date: String, user: String, type: String) {
        if (name.isNotEmpty() && count.isNotEmpty() && date.isNotEmpty() && user.isNotEmpty()) {
            viewModelScope.launch {
                _addItemUseCase.addItem(
                    Item(
                        name = name,
                        count = count,
                        date = date,
                        user = user,
                        type = type
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

    fun updateItemInDatabase(id: Int, name: String, count: String, date: String, user: String, type:String) {
        if (name.isNotEmpty() && count.isNotEmpty() && date.isNotEmpty() && user.isNotEmpty()) {
            viewModelScope.launch {
                _updateItemUseCase.updateItem(
                    Item(
                        id = id,
                        name = name,
                        count = count,
                        date = date,
                        user = user,
                        type = type
                    )
                )
            }
            _stateEditItemLiveData.postValue(Status.SUCCESS)
        } else {
            _stateEditItemLiveData.postValue(Status.ERROR)
        }
    }
}