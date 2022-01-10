package ru.glushko.dnkstockapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.domain.usecases.item.*

class ReviewViewModel(
    private val _deleteItemUseCase: DeleteItemUseCase,
    private val _updateItemUseCase: UpdateItemUseCase,
    private val _addItemUseCase: AddItemUseCase,
    private val _loadConsumablesItemsUseCase: LoadConsumablesItemsUseCase,
    private val _loadHardwareItemsUseCase: LoadHardwareItemsUseCase,
) : ViewModel() {

    val transactionStatus = MutableLiveData<String>()

    val consumablesItems: LiveData<List<Item>> by lazy {_loadConsumablesItemsUseCase.getConsumablesItems() }
    val hardwareItems: LiveData<List<Item>> by lazy { _loadHardwareItemsUseCase.getHardwareItems() }

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
        } else
            transactionStatus.postValue("Ошибка. Введите все данные!")
    }

    fun deleteItemFromDatabase(item: Item) = viewModelScope.launch {
        _deleteItemUseCase.deleteItem(item)
    }

    fun updateItemInDatabase(
        id: Int,
        name: String,
        count: String,
        date: String,
        user: String,
        type: String
    ) {
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
        } else
            transactionStatus.postValue("Ошибка. Введите все данные!")
    }
}