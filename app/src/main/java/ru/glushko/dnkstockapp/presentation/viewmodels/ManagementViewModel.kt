package ru.glushko.dnkstockapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.glushko.dnkstockapp.domain.model.ArchiveItem
import ru.glushko.dnkstockapp.domain.model.Staff
import ru.glushko.dnkstockapp.domain.model.StockItem
import ru.glushko.dnkstockapp.domain.usecases.archive.DeleteArchiveItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.archive.LoadAllArchiveItemsUseCase
import ru.glushko.dnkstockapp.domain.usecases.staff.AddStaffUseCase
import ru.glushko.dnkstockapp.domain.usecases.staff.DeleteStaffUseCase
import ru.glushko.dnkstockapp.domain.usecases.staff.LoadAllStaffUseCase
import ru.glushko.dnkstockapp.domain.usecases.staff.UpdateStaffUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.AddStockItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.DeleteStockItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.LoadAllStockItemsUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.UpdateStockItemUseCase

class ManagementViewModel(
    private val _deleteStockItemUseCase: DeleteStockItemUseCase,
    private val _updateStockItemUseCase: UpdateStockItemUseCase,
    private val _addItemStockUseCase: AddStockItemUseCase,
    private val _loadAllStockItemsUseCase: LoadAllStockItemsUseCase,
    private val _loadAllStaffUseCase: LoadAllStaffUseCase,
    private val _addStaffUseCase: AddStaffUseCase,
    private val _updateStaffUseCase: UpdateStaffUseCase,
    private val _deleteStaffUseCase: DeleteStaffUseCase,
    private val _loadAllArchiveItemsUseCase: LoadAllArchiveItemsUseCase,
    private val _deleteArchiveItemUseCase: DeleteArchiveItemUseCase,
) : ViewModel() {

    val transactionStatus = MutableLiveData<String>()

    val allStockItems: LiveData<List<StockItem>> by lazy { _loadAllStockItemsUseCase.loadAllStockItems() }
    val allStaff: LiveData<List<Staff>> by lazy { _loadAllStaffUseCase.loadAllStaff() }
    val allArchiveItems: LiveData<List<ArchiveItem>> by lazy { _loadAllArchiveItemsUseCase.loadAllArchiveItems() }


    fun addStockItemToDatabase(name: String, count: Int, balance: Int) {
        if (name.isNotEmpty() && count > 0 && balance > 0) {
            viewModelScope.launch {
                _addItemStockUseCase.addStockItem(
                    StockItem(
                        name = name,
                        count = count,
                        balance = balance
                    )
                )
            }
            transactionStatus.postValue("Запись успешно добавлена!")
        } else
            transactionStatus.postValue("Ошибка. Введите все данные!")
    }

    fun deleteStockItemFromDatabase(stockItem: StockItem) = viewModelScope.launch {
        _deleteStockItemUseCase.deleteStockItem(stockItem)
    }

    fun updateStockItemInDatabase(id: Int, name: String, count: Int, balance: Int) {
        if (name.isNotEmpty() && count > 0 && balance > 0) {
            viewModelScope.launch {
                _updateStockItemUseCase.updateStockItem(
                    StockItem(
                        id = id,
                        name = name,
                        count = count,
                        balance = balance
                    )
                )
            }
            transactionStatus.postValue("Запись успешно обновлена!")
        } else
            transactionStatus.postValue("Ошибка. Введите все данные!")
    }

    fun addStaffToDatabase(surname: String, name: String, lastname: String) {
        if (surname.isNotEmpty() && name.isNotEmpty() && lastname.isNotEmpty()) {
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

    fun deleteStaffFromDatabase(staff: Staff) = viewModelScope.launch {
        _deleteStaffUseCase.deleteStaff(staff)
    }

    fun updateStaffInDatabase(id: Int, surname: String, name: String, lastname: String) {
        if (surname.isNotEmpty() && name.isNotEmpty() && lastname.isNotEmpty()) {
            viewModelScope.launch {
                _updateStaffUseCase.updateStaff(
                    Staff(
                        id = id,
                        surname = surname,
                        name = name,
                        lastname = lastname
                    )
                )
            }
            transactionStatus.postValue("Запись успешно обновлена!")
        } else
            transactionStatus.postValue("Ошибка. Введите все данные!")
    }

    fun deleteArchiveItemFromDatabase(archiveItem: ArchiveItem) = viewModelScope.launch {
        _deleteArchiveItemUseCase.deleteArchiveItem(archiveItem)
    }
}