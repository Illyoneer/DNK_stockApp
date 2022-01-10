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
            _loadHardwareItemsUseCase = get()
        )
    }

    viewModel {
        ManagementViewModel(
            _addItemStockUseCase = get(),
            _deleteStockItemUseCase = get(),
            _updateStockItemUseCase = get(),
            _loadAllStockItemsUseCase = get()
        )
    }
}