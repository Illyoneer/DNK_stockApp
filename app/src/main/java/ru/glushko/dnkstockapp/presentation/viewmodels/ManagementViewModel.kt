package ru.glushko.dnkstockapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.glushko.dnkstockapp.domain.entity.Staff
import ru.glushko.dnkstockapp.domain.entity.StockItem
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
    private val _deleteStaffUseCase: DeleteStaffUseCase
) : ViewModel() {

    val transactionStatus = MutableLiveData<String>()

    val allStockItems: LiveData<List<StockItem>> by lazy { _loadAllStockItemsUseCase.loadAllStockItems() }
    val allStaff: LiveData<List<Staff>> by lazy { _loadAllStaffUseCase.loadAllStaff() }


    fun addStockItemToDatabase(name: String, count: String) {
        if (name.isNotEmpty() && count.isNotEmpty()) {
            viewModelScope.launch {
                _addItemStockUseCase.addStockItem(
                    StockItem(
                        name = name,
                        count = count
                    )
                )
            }
        } else
            transactionStatus.postValue("Ошибка. Введите все данные!")
    }

    fun deleteStockItemFromDatabase(stockItem: StockItem) = viewModelScope.launch {
        _deleteStockItemUseCase.deleteStockItem(stockItem)
    }

    fun updateStockItemInDatabase(id: Int, name: String, count: String) {
        if (name.isNotEmpty() && count.isNotEmpty()) {
            viewModelScope.launch {
                _updateStockItemUseCase.updateStockItem(
                    StockItem(
                        id = id,
                        name = name,
                        count = count
                    )
                )
            }
        } else
            transactionStatus.postValue("Ошибка. Введите все данные!")
    }

    fun addStaffToDatabase(surname: String, name: String, lastname: String){
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
        } else
            transactionStatus.postValue("Ошибка. Введите все данные!")
    }

    fun deleteStaffFromDatabase(staff: Staff) = viewModelScope.launch {
        _deleteStaffUseCase.deleteStaff(staff)
    }

    fun updateStaffInDatabase(id:Int, surname: String, name: String, lastname: String){
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
        } else
            transactionStatus.postValue("Ошибка. Введите все данные!")
    }
}