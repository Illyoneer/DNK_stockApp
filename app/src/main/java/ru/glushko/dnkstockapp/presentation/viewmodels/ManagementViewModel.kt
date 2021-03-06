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
import ru.glushko.dnkstockapp.domain.usecases.stockitem.*

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
    private val _updateStockItemBalanceUseCase: UpdateStockItemBalanceUseCase
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
            transactionStatus.postValue("???????????? ?????????????? ??????????????????!")
        } else
            transactionStatus.postValue("????????????. ?????????????? ?????? ????????????!")
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
            transactionStatus.postValue("???????????? ?????????????? ??????????????????!")
        } else
            transactionStatus.postValue("????????????. ?????????????? ?????? ????????????!")
    }

    fun updateStockItemBalanceInDatabase(incoming_count: Int, stock_item_name: String) {
        if (incoming_count > 0 && stock_item_name.isNotEmpty()) {
            viewModelScope.launch {
                _updateStockItemBalanceUseCase.updateStockItemBalance(
                    incoming_count = incoming_count,
                    stock_item_name = stock_item_name)
            }
            transactionStatus.postValue("?????????????????????? ?????????????? ??????????????????!")
        } else
            transactionStatus.postValue("????????????. ?????????????? ?????? ????????????!")
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
            transactionStatus.postValue("???????????? ?????????????? ??????????????????!")
        } else
            transactionStatus.postValue("????????????. ?????????????? ?????? ????????????!")
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
            transactionStatus.postValue("???????????? ?????????????? ??????????????????!")
        } else
            transactionStatus.postValue("????????????. ?????????????? ?????? ????????????!")
    }

    fun deleteArchiveItemFromDatabase(archiveItem: ArchiveItem) = viewModelScope.launch {
        _deleteArchiveItemUseCase.deleteArchiveItem(archiveItem)
    }
}