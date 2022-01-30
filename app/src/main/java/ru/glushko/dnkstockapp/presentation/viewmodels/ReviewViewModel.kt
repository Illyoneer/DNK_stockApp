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
import ru.glushko.dnkstockapp.domain.usecases.staff.AddStaffUseCase
import ru.glushko.dnkstockapp.domain.usecases.staff.LoadAllStaffUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.LoadAllStockItemsUseCase

class ReviewViewModel(
    private val _deleteItemWithUpdateStockUseCase: DeleteItemWithUpdateStockUseCase,
    private val _updateItemWithUpdateStockUseCases: UpdateItemWithUpdateStockUseCases,
    private val _addItemWithUpdateStock: AddItemWithUpdateStockUseCase,
    private val _loadConsumablesItemsUseCase: LoadConsumablesItemsUseCase,
    private val _loadHardwareItemsUseCase: LoadHardwareItemsUseCase,
    private val _loadAllStockItemsUseCase: LoadAllStockItemsUseCase,
    private val _loadAllStaffUseCase: LoadAllStaffUseCase,
    private val _addArchiveItemUseCase: AddArchiveItemUseCase,
    private val _addStaffUseCase: AddStaffUseCase
) : ViewModel() {

    val transactionStatus = MutableLiveData<String>()

    val consumablesItems: LiveData<List<Item>> by lazy { _loadConsumablesItemsUseCase.getConsumablesItems() }
    val hardwareItems: LiveData<List<Item>> by lazy { _loadHardwareItemsUseCase.getHardwareItems() }

    val allStockItems: LiveData<List<StockItem>> by lazy { _loadAllStockItemsUseCase.loadAllStockItems() }
    val allStaff: LiveData<List<Staff>> by lazy { _loadAllStaffUseCase.loadAllStaff() }

    fun addItemToDatabase(
        name: String,
        count: Int,
        date: String,
        user: String,
        type: String,
    ) {
        if (name.isNotEmpty() && count > 0 && date.isNotEmpty() && user.isNotEmpty()) {
                viewModelScope.launch {
                    _addItemWithUpdateStock.addItemWithUpdateStock(
                        Item(
                            name = name,
                            count = count,
                            date = date,
                            user = user,
                            type = type
                        )
                    )
                }
            transactionStatus.postValue("Запись успешно добавлена!")
        } else
            transactionStatus.postValue("Ошибка. Введите все данные!")
    }

    fun deleteItemFromDatabase(item: Item) = viewModelScope.launch {
        _deleteItemWithUpdateStockUseCase.deleteItemWithUpdateStock(item)
    }

    fun updateItemInDatabase(
        id: Int,
        name: String,
        count: Int,
        date: String,
        user: String,
        type: String,
        start_count: Int
    ) {
        if (name.isNotEmpty() && count > 0 && date.isNotEmpty() && user.isNotEmpty()) {
                viewModelScope.launch {
                    _updateItemWithUpdateStockUseCases.updateItem(
                        Item(
                            id = id,
                            name = name,
                            count = count,
                            date = date,
                            user = user,
                            type = type
                        ), start_count
                    )
                }
            transactionStatus.postValue("Запись успешно обновлена!")
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
        _deleteItemWithUpdateStockUseCase.deleteItemWithUpdateStock(item)
    }

    fun addStaffToDatabase(surname: String, name: String, lastname: String) {
        if (surname.isNotEmpty() && name.isNotEmpty()) {
            viewModelScope.launch {
                _addStaffUseCase.addStaff(
                    Staff(
                        surname = surname,
                        name = name,
                        lastname = lastname
                    )
                )
            }
            transactionStatus.postValue("Запись успешно добавлена!")
        } else
            transactionStatus.postValue("Ошибка. Введите все данные!")
    }
}