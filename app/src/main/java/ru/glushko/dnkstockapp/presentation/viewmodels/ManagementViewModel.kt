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
import ru.glushko.dnkstockapp.presentation.viewutils.Status

class ManagementViewModel (
    private val _deleteStockItemUseCase: DeleteStockItemUseCase,
    private val _updateStockItemUseCase: UpdateStockItemUseCase,
    private val _addItemStockUseCase: AddStockItemUseCase,
    private val _loadAllStockItemsUseCase: LoadAllStockItemsUseCase,
) : ViewModel() {

    private val _stateAddStockItemLiveData = MutableLiveData<Status>()
    private val _stateEditStockItemLiveData = MutableLiveData<Status>()


    fun getStateAddItemLiveData(): MutableLiveData<Status> = _stateAddStockItemLiveData

    fun getStateEditItemLiveData(): MutableLiveData<Status> = _stateEditStockItemLiveData

    fun loadAllStockItems(): LiveData<List<StockItem>> = _loadAllStockItemsUseCase.loadAllStockItems()


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
            _stateAddStockItemLiveData.postValue(Status.SUCCESS)
        } else {
            _stateAddStockItemLiveData.postValue(Status.ERROR)
        }
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
            _stateEditStockItemLiveData.postValue(Status.SUCCESS)
        } else {
            _stateEditStockItemLiveData.postValue(Status.ERROR)
        }
    }
}