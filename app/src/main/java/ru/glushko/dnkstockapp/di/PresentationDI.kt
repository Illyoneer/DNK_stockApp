package ru.glushko.dnkstockapp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.glushko.dnkstockapp.presentation.viewmodels.ManagementViewModel
import ru.glushko.dnkstockapp.presentation.viewmodels.ReviewViewModel


val presentationModule = module {
    viewModel {
        ReviewViewModel(
            _addItemUseCase = get(),
            _deleteItemUseCase = get(),
            _updateItemUseCase = get(),
            _loadConsumablesItemsUseCase = get(),
            _loadHardwareItemsUseCase = get(),
            _loadAllStockItemsUseCase = get(),
            _loadAllStaffUseCase = get(),
            _addArchiveItemUseCase = get()
        )
    }

    viewModel {
        ManagementViewModel(
            _addItemStockUseCase = get(),
            _deleteStockItemUseCase = get(),
            _updateStockItemUseCase = get(),
            _loadAllStockItemsUseCase = get(),
            _loadAllStaffUseCase = get(),
            _addStaffUseCase = get(),
            _deleteStaffUseCase = get(),
            _updateStaffUseCase = get(),
            _addArchiveItemUseCase = get(),
            _deleteArchiveItemUseCase = get(),
            _loadAllArchiveItemsUseCase = get()
        )
    }
}