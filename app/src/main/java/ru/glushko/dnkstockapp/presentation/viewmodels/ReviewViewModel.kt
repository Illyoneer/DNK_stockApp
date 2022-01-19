package ru.glushko.dnkstockapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.glushko.dnkstockapp.domain.model.ArchiveItem
import ru.glushko.dnkstockapp.domain.model.Item
import ru.glushko.dnkstockapp.domain.model.Staff
import ru.glushko.dnkstockapp.domain.model.StockItem
import ru.glushko.dnkstockapp.domain.usecases.archive.AddArchiveItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.item.*
import ru.glushko.dnkstockapp.domain.usecases.staff.LoadAllStaffUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.LoadAllStockItemsUseCase

class ReviewViewModel(
    private val _deleteItemUseCase: DeleteItemUseCase,
    private val _updateItemUseCase: UpdateItemUseCase,
    private val _addItemUseCase: AddItemUseCase,
    private val _loadConsumablesItemsUseCase: LoadConsumablesItemsUseCase,
    private val _loadHardwareItemsUseCase: LoadHardwareItemsUseCase,
    private val _loadAllStockItemsUseCase: LoadAllStockItemsUseCase,
    private val _loadAllStaffUseCase: LoadAllStaffUseCase,
    private val _addArchiveItemUseCase: AddArchiveItemUseCase
) : ViewModel() {

    val transactionStatus = MutableLiveData<String>()

    val consumablesItems: LiveData<List<Item>> by lazy { _loadConsumablesItemsUseCase.getConsumablesItems() }
    val hardwareItems: LiveData<List<Item>> by lazy { _loadHardwareItemsUseCase.getHardwareItems() }

    val allStockItems: LiveData<List<StockItem>> by lazy { _loadAllStockItemsUseCase.loadAllStockItems() }
    val allStaff: LiveData<List<Staff>> by lazy { _loadAllStaffUseCase.loadAllStaff() }

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

    fun updateItemInDatabase(id: Int, name: String, count: String, date: String, user: String, type: String) {
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

    fun moveItemToArchive(item: Item, dateToday: String) = viewModelScope.launch {
        _addArchiveItemUseCase.addArchiveItem(
            ArchiveItem(
                name = item.name,
                count = item.count,
                date = dateToday,
                user = item.user,
                type = item.type
            )
        )
        _deleteItemUseCase.deleteItem(item)
    }
}