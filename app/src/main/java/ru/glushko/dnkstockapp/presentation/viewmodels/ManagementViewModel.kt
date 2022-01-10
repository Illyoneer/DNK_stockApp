package ru.glushko.dnkstockapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.glushko.dnkstockapp.domain.StockItem
import ru.glushko.dnkstockapp.domain.usecases.stockitem.AddStockItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.DeleteStockItemUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.LoadAllStockItemsUseCase
import ru.glushko.dnkstockapp.domain.usecases.stockitem.UpdateStockItemUseCase

class ManagementViewModel(
    private val _deleteStockItemUseCase: DeleteStockItemUseCase,
    private val _updateStockItemUseCase: UpdateStockItemUseCase,
    private val _addItemStockUseCase: AddStockItemUseCase,
    private val _loadAllStockItemsUseCase: LoadAllStockItemsUseCase,
) : ViewModel() {

    val transactionStatus = MutableLiveData<String>()

    val allStockItems: LiveData<List<StockItem>> by lazy { _loadAllStockItemsUseCase.loadAllStockItems() }


    fun addItemToDatabase(name: String, count: String) {
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

    fun deleteItemFromDatabase(stockItem: StockItem) = viewModelScope.launch {
        _deleteStockItemUseCase.deleteStockItem(stockItem)
    }

    fun updateItemInDatabase(id: Int, name: String, count: String) {
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
}